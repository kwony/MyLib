package com.kwony.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kwony.data.dao.BookMemoDao
import com.kwony.data.dao.BookSearchDao
import com.kwony.data.vo.BookMemo
import com.kwony.data.vo.BookSearch

@Database(
    entities = [BookSearch::class, BookMemo::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookSearchDao() : BookSearchDao

    abstract fun bookMemoDao() : BookMemoDao
}