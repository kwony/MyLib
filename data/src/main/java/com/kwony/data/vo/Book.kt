package com.kwony.data.vo

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(primaryKeys = ["isbn13"])
data class Book(
        val title: String,
        val subTitle: String?,
        val isbn13: Long,
        val price: String,
        val image: String,
        val url: String
)