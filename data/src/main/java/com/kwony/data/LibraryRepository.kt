package com.kwony.data

import com.kwony.data.dao.BookDetailDao
import com.kwony.data.dao.BookRelationDao
import com.kwony.data.dao.BookSearchDao
import com.kwony.data.vo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.Exception

class LibraryRepository(
    private val apiProvider: ApiProvider,
    private val responseHandler: ResponseHandler,
    private val bookDetailDao: BookDetailDao,
    private val bookRelationDao: BookRelationDao,
    private val bookSearchDao: BookSearchDao
) {

    private val libraryApi by lazy { apiProvider.createApi(LibraryApi::class.java) }

    suspend fun loadNew(): Resource<BookListResp> = withContext(Dispatchers.IO) {
        return@withContext try {
            libraryApi.getNew().run {
                responseHandler.handleSuccess(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            responseHandler.handleException(e)
        }
    }

    suspend fun loadSearch(query: String, page: Int): Resource<BookListResp> = withContext(Dispatchers.IO) {
        return@withContext try {
            libraryApi.getSearchResult(query, page).run {
                responseHandler.handleSuccess(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            responseHandler.handleException(e)
        }
    }

    suspend fun loadBookMark(): Flow<List<BookDetail>?> = withContext(Dispatchers.IO) {
        return@withContext bookRelationDao.selectBooksByRelation(BookRelationType.BOOK_MARK.code)
    }

    suspend fun loadBookDetail(isbn13: Long): Resource<BookDetail> = withContext(Dispatchers.IO) {
        return@withContext try {
            libraryApi.getBookDetail(isbn13).run {
                bookDetailDao.upsert(listOf(this))
                responseHandler.handleSuccess(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            responseHandler.handleException(e)
        }
    }

    suspend fun loadBookSearchHistory(): Flow<List<BookSearch>?> = withContext(Dispatchers.IO) {
        return@withContext bookSearchDao.selectBookSearchList()
    }

    suspend fun addBookmark(isbn13: Long) = withContext(Dispatchers.IO) {
        bookRelationDao.upsert(BookRelation.create(isbn13, BookRelationType.BOOK_MARK))
    }

    suspend fun removeBookmark(isbn13: Long) = withContext(Dispatchers.IO) {
        bookRelationDao.upsert(BookRelation.create(isbn13, BookRelationType.NONE))
    }

    suspend fun addBookSearch(query: String) = withContext(Dispatchers.IO) {
        bookSearchDao.upsert(BookSearch(query))
    }

    suspend fun removeBookSearch(query: String) = withContext(Dispatchers.IO) {
        bookSearchDao.deleteQuery(query)
    }

    suspend fun loadBookRelation(isbn13: Long): Flow<BookRelation?> = withContext(Dispatchers.IO) {
        return@withContext bookRelationDao.selectBookRelationByIsbn(isbn13)
    }
}