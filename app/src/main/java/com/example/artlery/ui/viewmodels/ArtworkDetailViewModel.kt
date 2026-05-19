package com.example.artlery.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.artlery.data.local.ArtDatabase
import com.example.artlery.data.local.ArtworkEntity
import com.example.artlery.data.local.CommentEntity
import com.example.artlery.data.repository.ArtRepository
import com.example.artlery.model.Piece
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface DetailUiState {
    object Loading : DetailUiState
    data class Success(val piece: Piece, val comments: List<CommentEntity>) : DetailUiState
    object Error : DetailUiState
}

@OptIn(ExperimentalCoroutinesApi::class)
class ArtworkDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = ArtDatabase.getDatabase(application).artDao()
    private val repository = ArtRepository(dao)

    private val _artworkId = MutableStateFlow<Int?>(null)
    val artworkId: StateFlow<Int?> = _artworkId.asStateFlow()

    val isFavorite: StateFlow<Boolean> = _artworkId.flatMapLatest { id ->
        id?.let { repository.isFavorite(it) } ?: flowOf(false)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private var currentArtworkDto: ArtworkEntity? = null

    val uiState: StateFlow<DetailUiState> = _artworkId.flatMapLatest { id ->
        if (id == null) return@flatMapLatest flowOf(DetailUiState.Error)

        val commentsFlow = repository.getComments(id)

        val artworkFlow = kotlinx.coroutines.flow.flow {
            val localArtwork = repository.getFavoriteById(id)
            if (localArtwork != null) {
                currentArtworkDto = localArtwork
                emit(mapEntityToPiece(localArtwork))
            } else {
                val apiArtwork = repository.fetchArtworkDetailFromApi(id)
                if (apiArtwork != null) {
                    val entity = ArtworkEntity(
                        id = apiArtwork.id,
                        title = apiArtwork.title,
                        artist = apiArtwork.artist ?: "Artista Desconocido",
                        date = apiArtwork.date ?: "Año Desconocido",
                        style = apiArtwork.style ?: "Estilo Desconocido",
                        location = "Art Institute of Chicago",
                        description = apiArtwork.description ?: "Sin descripción.",
                        imageUrl = apiArtwork.imageUrl
                    )
                    currentArtworkDto = entity
                    emit(mapEntityToPiece(entity))
                } else {
                    emit(null)
                }
            }
        }

        combine(artworkFlow, commentsFlow) { piece, commentsList ->
            if (piece != null) {
                DetailUiState.Success(piece, commentsList)
            } else {
                DetailUiState.Error
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DetailUiState.Loading)

    fun loadArtwork(id: Int?) {
        if (id == null) return
        _artworkId.value = id
    }

    fun toggleFavorite(onDuplicateAlert: () -> Unit) {
        val entity = currentArtworkDto ?: return
        viewModelScope.launch {
            if (isFavorite.value) {
                repository.removeFavorite(entity)
            } else {
                repository.saveFavorite(entity)
            }
        }
    }

    fun addComment(author: String, text: String, timestamp: String) {
        val id = _artworkId.value ?: return
        viewModelScope.launch {
            repository.addComment(
                CommentEntity(
                    artworkId = id,
                    author = author,
                    text = text,
                    timestamp = timestamp
                )
            )
        }
    }

    private fun cleanHtml(text: String?): String {
        if (text == null) return "Sin descripción disponible."
        return text.replace(Regex("<[^>]*>"), "").trim()
    }

    private fun mapEntityToPiece(entity: ArtworkEntity): Piece {
        return Piece(
            id = entity.id,
            name = entity.title,
            author = entity.artist,
            year = entity.date,
            style = entity.style,
            location = entity.location,
            description = cleanHtml(entity.description),
            photo = entity.imageUrl,
            isFav = true
        )
    }
}