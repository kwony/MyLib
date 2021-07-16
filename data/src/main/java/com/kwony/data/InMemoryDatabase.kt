package com.kwony.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kwony.data.dao.BookDao
import com.kwony.data.dao.BookRelationDao
import com.kwony.data.vo.Book
import com.kwony.data.vo.BookRelation

@Database(
    entities = [Book::class, BookRelation::class],
    version = 1
)
abstract class InMemoryDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
//
    abstract fun bookRelationDao(): BookRelationDao
}