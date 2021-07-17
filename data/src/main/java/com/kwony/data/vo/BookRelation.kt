package com.kwony.data.vo

import androidx.room.Entity
import androidx.room.Ignore

@Entity(primaryKeys = ["isbn13"])
data class BookRelation(
    val isbn13: Long,
    val _relationType: String
) {
    @Ignore
    val relationType = BookRelationType.toEnum(_relationType)

    companion object {
        @JvmStatic
        fun create(isbn13: Long, relationType: BookRelationType) = BookRelation(isbn13, relationType.code)
    }
}

enum class BookRelationType(val code: String) {
    NONE("none"), BOOK_MARK("bookmark");

    companion object {
        @JvmStatic
        fun toEnum(value: String) = values().firstOrNull { value == it.code } ?: NONE
    }
}
