package com.kwony.data

import com.kwony.data.dao.BookDetailDao
import com.kwony.data.dao.BookMemoDao
import com.kwony.data.dao.BookRelationDao
import com.kwony.data.dao.BookSearchDao
import com.kwony.data.vo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.lang.IllegalArgumentException

class LibraryRepository(
    private val apiProvider: ApiProvider,
    private val bookDetailDao: BookDetailDao,
    private val bookRelationDao: BookRelationDao,
    private val bookSearchDao: BookSearchDao,
    private val bookMemoDao: BookMemoDao
) {

    private val libraryApi by lazy { apiProvider.createApi(LibraryApi::class.java) }

    suspend fun loadNew(): BookListResp = withContext(Dispatchers.IO) {
        return@withContext libraryApi.getNew()
    }

    suspend fun loadSearch(query: String, page: Int): BookListResp = withContext(Dispatchers.IO) {
        return@withContext libraryApi.getSearchResult(query, page)
    }

    suspend fun loadBookMark(): Flow<List<BookDetail>?> = withContext(Dispatchers.IO) {
        return@withContext bookRelationDao.selectBooksByRelation(BookRelationType.BOOK_MARK.code)
    }

    suspend fun loadBookDetail(isbn13: Long): BookDetail = withContext(Dispatchers.IO) {
        return@withContext libraryApi.getBookDetail(isbn13).also {
            bookDetailDao.upsert(listOf(it))
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

    suspend fun addBookMemo(isbn13: Long, memo: String) = withContext(Dispatchers.IO) {
        bookMemoDao.upsert(BookMemo(isbn13, memo))
    }

    suspend fun deleteBookMemo(isbn13: Long) = withContext(Dispatchers.IO) {
        bookMemoDao.deleteBookMemo(isbn13)
    }

    suspend fun loadBookMemo(isbn13: Long) = withContext(Dispatchers.IO) {
        return@withContext bookMemoDao.selectBookMemoByIsbn(isbn13)
    }
}