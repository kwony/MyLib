package com.kwony.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class LibraryRepository(private val apiProvider: ApiProvider, private val responseHandler: ResponseHandler) {

    private val libraryApi by lazy { apiProvider.createApi(LibraryApi::class.java) }

    suspend fun loadNew(): Resource<BookListResp> = withContext(Dispatchers.IO) {
        return@withContext try {
            responseHandler.handleSuccess(libraryApi.getNew())
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun loadSearch(query: String, page: Int): Resource<BookListResp> = withContext(Dispatchers.IO) {
        return@withContext try {
            responseHandler.handleSuccess(libraryApi.getSearchResult(query, page))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}