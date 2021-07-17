package com.kwony.data

import com.kwony.data.dao.BookDao
import com.kwony.data.dao.BookRelationDao
import com.kwony.data.vo.Book
import com.kwony.data.vo.BookRelationType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.Exception

class LibraryRepository(private val apiProvider: ApiProvider, private val responseHandler: ResponseHandler, private val bookDao: BookDao, private val bookRelationDao: BookRelationDao) {

    private val libraryApi by lazy { apiProvider.createApi(LibraryApi::class.java) }

    suspend fun loadNew(): Resource<BookListResp> = withContext(Dispatchers.IO) {
        return@withContext try {
            libraryApi.getNew().run {
                bookDao.upsert(this.books)
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
                bookDao.upsert(this.books)
                responseHandler.handleSuccess(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            responseHandler.handleException(e)
        }
    }

    suspend fun loadBookMark(): Flow<List<Book>?> = withContext(Dispatchers.IO) {
        return@withContext bookRelationDao.selectBookByRelation(BookRelationType.BOOK_MARK.code)
    }
}