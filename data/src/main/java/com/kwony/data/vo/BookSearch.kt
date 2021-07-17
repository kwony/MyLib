package com.kwony.data.vo

import androidx.room.Entity

@Entity(primaryKeys = ["query"])
data class BookSearch(val query: String, val createdAt: Long = System.currentTimeMillis())
