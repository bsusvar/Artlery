package com.example.artlery.model

data class Piece(
    val name: String,
    val photo: String,
    val author: String,
    val year: String,
    val style: String,
    val location: String,
    val description: String,
    var isFav: Boolean = false
)