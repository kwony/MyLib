package com.kwony.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kwony.data.vo.BookSearch
import kotlinx.coroutines.flow.Flow

@Dao
interface BookSearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(bookSearch: BookSearch): Long

    @Query("select * from BookSearch order by BookSearch.createdAt desc")
    fun selectBookSearchList(): Flow<List<BookSearch>?>

    @Query("delete from BookSearch where `query` = :query")
    fun deleteQuery(query: String)
}