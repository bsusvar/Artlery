package com.example.artlery.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(artwork: ArtworkEntity)

    @Delete
    suspend fun deleteFavorite(artwork: ArtworkEntity)

    @Query("SELECT * FROM favorite_artworks")
    fun getAllFavorites(): Flow<List<ArtworkEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_artworks WHERE id = :id)")
    fun isFavorite(id: Int): Flow<Boolean>

    @Query("SELECT * FROM favorite_artworks WHERE id = :id")
    suspend fun getFavoriteById(id: Int): ArtworkEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentEntity)

    @Query("SELECT * FROM artwork_comments WHERE artworkId = :artworkId ORDER BY id DESC")
    fun getCommentsForArtwork(artworkId: Int): Flow<List<CommentEntity>>
}