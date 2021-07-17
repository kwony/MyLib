package com.kwony.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kwony.data.dao.BookDetailDao
import com.kwony.data.dao.BookRelationDao
import com.kwony.data.vo.BookDetail
import com.kwony.data.vo.BookRelation

@Database(
    entities = [BookDetail::class, BookRelation::class],
    version = 1
)
abstract class InMemoryDatabase: RoomDatabase() {
    abstract fun bookDetailDao(): BookDetailDao

    abstract fun bookRelationDao(): BookRelationDao
}