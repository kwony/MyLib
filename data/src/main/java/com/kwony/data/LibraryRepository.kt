package com.kwony.data

import java.lang.Exception

class LibraryRepository(private val apiProvider: ApiProvider, private val responseHandler: ResponseHandler) {

    private val libraryApi by lazy { apiProvider.createApi(LibraryApi::class.java) }

    suspend fun loadNew(): Resource<LibraryNewResp> {
        return try {
            responseHandler.handleSuccess(libraryApi.getNew())
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}