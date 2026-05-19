package com.example.artlery.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtworkResponse(
    @SerialName("data") val data: List<ArtworkDto>
)

@Serializable
data class ArtworkDetailResponse(
    @SerialName("data") val data: ArtworkDto
)

@Serializable
data class ArtworkDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("artist_display") val artist: String? = "Artista Desconocido",
    @SerialName("date_display") val date: String? = "Año Desconocido",
    @SerialName("style_title") val style: String? = "Estilo Desconocido",
    @SerialName("image_id") val imageId: String? = null,
    @SerialName("description") val description: String? = "Sin descripción disponible."
) {
    val imageUrl: String
        get() = if (!imageId.isNullOrBlank()) "https://www.artic.edu/iiif/2/$imageId/full/843,/0/default.jpg" else ""
}