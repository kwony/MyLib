package com.kwony.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kwony.data.vo.BookMemo
import com.kwony.data.vo.BookSearch
import kotlinx.coroutines.flow.Flow

@Dao
interface BookMemoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(bookMemo: BookMemo): Long

    @Query("select * from BookMemo where isbn = :isbn")
    fun selectBookMemoByIsbn(isbn: Long): Flow<BookMemo?>

    @Query("delete from BookMemo where isbn = :isbn")
    fun deleteBookMemo(isbn: Long)
}