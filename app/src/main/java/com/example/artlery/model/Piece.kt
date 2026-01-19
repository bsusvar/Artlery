package com.example.artlery.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Piece(
    val name: String,
    val photo: String,
    val author: String,
    val year: String,
    val style: String,
    val location: String,
    val description: String,
    var isFavInitial: Boolean = false
) {
    var isFav by mutableStateOf(isFavInitial)

    fun containsText(query: String): Boolean {
        val fullData = "$name $author $year $style $location $description"
        return fullData.contains(query, ignoreCase = true)
    }
}