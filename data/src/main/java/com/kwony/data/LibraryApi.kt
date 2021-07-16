package com.kwony.data

import androidx.annotation.Keep
import com.kwony.data.vo.Book
import retrofit2.http.GET
import retrofit2.http.Path

interface LibraryApi {
    @GET("/1.0/new")
    suspend fun getNew() : BookListResp

    @GET("/1.0/search/{query}/{page}")
    suspend fun getSearchResult(@Path("query") query: String, @Path("page") page: Int) : BookListResp
}

@Keep
data class BookListResp(
    val error: Int,
    val total: Int,
    val books: List<Book>
)

