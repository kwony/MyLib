package com.kwony.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.kwony.data.vo.BookDetail

@Dao
interface BookDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(book: List<BookDetail>): List<Long>
}