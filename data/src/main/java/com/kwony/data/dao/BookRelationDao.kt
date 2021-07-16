package com.kwony.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.kwony.data.vo.BookRelation

@Dao
interface BookRelationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(bookList: List<BookRelation>)
}