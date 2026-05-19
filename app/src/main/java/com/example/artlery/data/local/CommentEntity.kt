package com.example.artlery.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artwork_comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val artworkId: Int,
    val author: String,
    val text: String,
    val timestamp: String
)