package com.example.artlery.data.repository

import com.example.artlery.data.api.ArtApi
import com.example.artlery.data.api.ArtworkDto
import com.example.artlery.data.local.ArtDao
import com.example.artlery.data.local.ArtworkEntity
import com.example.artlery.data.local.CommentEntity
import kotlinx.coroutines.flow.Flow

class ArtRepository(private val artDao: ArtDao) {

    private val apiService = ArtApi.retrofitService

    suspend fun fetchArtworks(): List<ArtworkDto> {
        return try {
            val response = apiService.getArtworks()
            response.data
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun fetchArtworkDetailFromApi(id: Int): ArtworkDto? {
        return try {
            val response = apiService.getArtworkDetail(id)
            response.data
        } catch (e: Exception) {
            null
        }
    }

    val allFavorites: Flow<List<ArtworkEntity>> = artDao.getAllFavorites()

    fun isFavorite(id: Int): Flow<Boolean> = artDao.isFavorite(id)

    suspend fun getFavoriteById(id: Int): ArtworkEntity? = artDao.getFavoriteById(id)

    suspend fun saveFavorite(artwork: ArtworkEntity) {
        artDao.insertFavorite(artwork)
    }

    suspend fun removeFavorite(artwork: ArtworkEntity) {
        artDao.deleteFavorite(artwork)
    }

    fun getComments(artworkId: Int): Flow<List<CommentEntity>> = artDao.getCommentsForArtwork(artworkId)

    suspend fun addComment(comment: CommentEntity) {
        artDao.insertComment(comment)
    }
}