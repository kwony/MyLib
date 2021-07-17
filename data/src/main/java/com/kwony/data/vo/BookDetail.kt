package com.kwony.data.vo

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

@Keep
@Entity(primaryKeys = ["isbn13"])
data class BookDetail(
    val title: String,
    val subtitle: String?,
    val authors: String?,
    val publisher: String?,
    val language: String?,
    val isbn10: String,
    val isbn13: Long,
    val pages: Int,
    val year: Int,
    val rating: Float,
    val desc: String,
    val price: String,
    val image: String,
    val url: String
) {
    @Ignore val pdf: BookDetailPdf? = null
}

@Keep
data class BookDetailPdf(
    @SerializedName("Free eBook")
    val freeEbook: String?
)