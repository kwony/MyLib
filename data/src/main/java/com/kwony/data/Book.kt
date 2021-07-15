package com.kwony.data

import androidx.annotation.Keep

@Keep
data class Book(
    val title: String,
    val subTitle: String,
    val isbn: Long,
    val price: String,
    val image: String,
    val url: String
)