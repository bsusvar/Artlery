package com.example.artlery.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Piece(
    val id: Int,
    val name: String,
    val author: String,
    val year: String,
    val style: String,
    val location: String,
    val description: String,
    val photo: String,
    var isFav: Boolean = false
) {
    fun containsText(query: String): Boolean {
        val fullData = "$id $name $author $year $style $location $description $photo $isFav"
        return fullData.contains(query, ignoreCase = true)
    }
}