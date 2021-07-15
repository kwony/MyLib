package com.kwony.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun provideApiProvider() = ApiProvider()

    @Singleton
    @Provides
    fun provideResponseHandler() = ResponseHandler()

    @Singleton
    @Provides
    fun provideLibraryRepository(apiProvider: ApiProvider, responseHandler: ResponseHandler) = LibraryRepository(apiProvider, responseHandler)
}