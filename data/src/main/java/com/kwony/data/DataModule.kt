package com.kwony.data

import android.content.Context
import androidx.room.Room
import com.kwony.data.dao.BookDetailDao
import com.kwony.data.dao.BookRelationDao
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
    fun provideInMemoryDatabase(context: Context): InMemoryDatabase {
        return Room.inMemoryDatabaseBuilder(context, InMemoryDatabase::class.java)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBookDao(inMemoryDatabase: InMemoryDatabase): BookDetailDao {
        return inMemoryDatabase.bookDetailDao()
    }

    @Singleton
    @Provides
    fun provideBookRelationDao(inMemoryDatabase: InMemoryDatabase): BookRelationDao {
        return inMemoryDatabase.bookRelationDao()
    }

    @Singleton
    @Provides
    fun provideLibraryRepository(apiProvider: ApiProvider, responseHandler: ResponseHandler, bookDetailDao: BookDetailDao, bookRelationDao: BookRelationDao): LibraryRepository {
        return LibraryRepository(apiProvider, responseHandler, bookDetailDao, bookRelationDao)
    }
}