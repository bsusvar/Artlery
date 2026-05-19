package com.example.artlery.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.artlery.data.local.ArtDatabase
import com.example.artlery.data.api.ArtworkDto
import com.example.artlery.data.repository.ArtRepository
import com.example.artlery.model.Piece
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface ArtUiState {
    object Loading : ArtUiState
    data class Success(val pieces: List<Piece>) : ArtUiState
    object Error : ArtUiState
}

class ArtApiViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = ArtDatabase.getDatabase(application).artDao()
    private val repository = ArtRepository(dao)

    private val _rawPieces = MutableStateFlow<List<Piece>>(emptyList())
    private val _isLoading = MutableStateFlow(true)
    private val _isError = MutableStateFlow(false)

    val uiState: StateFlow<ArtUiState> = combine(
        _rawPieces,
        repository.allFavorites,
        _isLoading,
        _isError
    ) { rawPieces, favoriteEntities, isLoading, isError ->
        when {
            isError -> ArtUiState.Error
            isLoading -> ArtUiState.Loading
            else -> {
                val favIds = favoriteEntities.map { it.id }.toSet()
                val synchronizedPieces = rawPieces.map { piece ->
                    piece.copy(isFav = favIds.contains(piece.id))
                }
                ArtUiState.Success(synchronizedPieces)
            }
        }
    }
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ArtUiState.Loading)

    init {
        loadApiArtworks()
    }

    fun loadApiArtworks() {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = false
            val dtos = repository.fetchArtworks()

            if (dtos.isNotEmpty()) {
                _rawPieces.value = dtos.map { mapDtoToPiece(it) }
                _isLoading.value = false
            } else {
                _isError.value = true
                _isLoading.value = false
            }
        }
    }

    private fun mapDtoToPiece(dto: ArtworkDto): Piece {
        return Piece(
            id = dto.id,
            name = dto.title,
            author = dto.artist ?: "Artista Desconocido",
            year = dto.date ?: "Año Desconocido",
            style = dto.style ?: "Estilo Desconocido",
            location = "Art Institute of Chicago",
            description = dto.description ?: "Sin descripción.",
            photo = dto.imageUrl,
            isFav = false
        )
    }
}