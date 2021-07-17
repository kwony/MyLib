package com.kwony.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kwony.data.vo.BookDetail
import com.kwony.data.vo.BookRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface BookRelationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(bookRelation: BookRelation)

    @Query("select * from BookDetail inner join BookRelation on BookDetail.isbn13 = BookRelation.isbn13 where BookRelation._relationType = :relationType")
    fun selectBooksByRelation(relationType: String): Flow<List<BookDetail>?>


    @Query("select * from BookRelation where BookRelation.isbn13 = :isbn13")
    fun selectBookRelationByIsbn(isbn13: Long): Flow<BookRelation?>
}