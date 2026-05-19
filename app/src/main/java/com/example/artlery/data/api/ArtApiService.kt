package com.example.artlery.data.api

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtApiService {

    @GET("artworks")
    suspend fun getArtworks(
        @Query("q") query: String = "masterpiece painting",
        @Query("limit") limit: Int = 50,
        @Query("fields") fields: String = "id,title,artist_display,image_id,description"
    ): ArtworkResponse

    @GET("artworks/{id}")
    suspend fun getArtworkDetail(
        @Path("id") id: Int,
        @Query("fields") fields: String = "id,title,artist_display,image_id,description"
    ): ArtworkDetailResponse
}

object ArtApi {
    private const val BASE_URL = "https://api.artic.edu/api/v1/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    val retrofitService: ArtApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ArtApiService::class.java)
    }
}