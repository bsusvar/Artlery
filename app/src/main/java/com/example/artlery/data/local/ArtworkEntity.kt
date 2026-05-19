package com.example.artlery.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_artworks")
data class ArtworkEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val artist: String,
    val date: String,
    val style: String,
    val location: String,
    val description: String,
    val imageUrl: String
)