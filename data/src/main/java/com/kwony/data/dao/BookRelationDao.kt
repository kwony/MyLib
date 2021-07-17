package com.kwony.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kwony.data.vo.Book
import com.kwony.data.vo.BookRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface BookRelationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(bookRelation: BookRelation)

    @Query("select * from Book inner join BookRelation on Book.isbn13 = BookRelation.isbn where BookRelation._relationType = :relationType")
    fun selectBookByRelation(relationType: String): Flow<List<Book>?>
}