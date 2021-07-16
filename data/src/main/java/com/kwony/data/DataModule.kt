package com.kwony.data

import android.content.Context
import androidx.room.Room
import com.kwony.data.dao.BookDao
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
    fun provideLibraryRepository(apiProvider: ApiProvider, responseHandler: ResponseHandler) = LibraryRepository(apiProvider, responseHandler)

//    @Singleton
//    @Provides
//    fun provideInMemoryDatabase(context: Context): InMemoryDatabase {
//        return Room.inMemoryDatabaseBuilder(context, InMemoryDatabase::class.java)
//            .fallbackToDestructiveMigration()
//            .build()
//    }

//    @Singleton
//    @Provides
//    fun provideBookDao(inMemoryDatabase: InMemoryDatabase): BookDao {
//        return inMemoryDatabase.bookDao()
//    }
//
//    @Singleton
//    @Provides
//    fun provideBookRelationDao(inMemoryDatabase: InMemoryDatabase): BookRelationDao {
//        return inMemoryDatabase.bookRelationDao()
//    }
}