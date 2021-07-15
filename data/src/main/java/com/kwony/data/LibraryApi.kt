package com.kwony.data

import androidx.annotation.Keep
import retrofit2.http.GET

interface LibraryApi {
    @GET("/1.0/new")
    suspend fun getNew() : LibraryNewResp
}

@Keep
data class LibraryNewResp(
    val error: Int,
    val total: Int,
    val books: List<Book>
)

