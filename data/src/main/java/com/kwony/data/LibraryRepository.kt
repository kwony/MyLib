package com.kwony.data

class LibraryRepository(private val apiProvider: ApiProvider) {
    private val libraryApi get() = apiProvider.createApi(LibraryApi::class.java)

    suspend fun loadNew() {

    }
}