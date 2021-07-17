package com.kwony.data.vo

import androidx.room.Entity

@Entity(primaryKeys = ["isbn"])
data class BookMemo(val isbn: Long, val memo: String)