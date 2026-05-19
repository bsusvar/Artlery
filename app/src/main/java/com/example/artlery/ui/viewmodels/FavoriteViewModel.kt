package com.example.artlery.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.artlery.data.local.ArtDatabase
import com.example.artlery.data.repository.ArtRepository
import com.example.artlery.model.Piece
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = ArtDatabase.getDatabase(application).artDao()
    private val repository = ArtRepository(dao)

    val favoritePieces: StateFlow<List<Piece>> = repository.allFavorites
        .map { entities ->
            entities.map { entity ->
                Piece(
                    id = entity.id,
                    name = entity.title,
                    author = entity.artist,
                    year = entity.date,
                    style = entity.style,
                    location = entity.location,
                    description = entity.description,
                    photo = entity.imageUrl,
                    isFav = true
                )
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}