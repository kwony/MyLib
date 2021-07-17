package com.kwony.mylib

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.needToLoadMoreLayoutManager(): Boolean {
    val lastVisiblePosition = (layoutManager as LinearLayoutManager)?.findLastVisibleItemPosition()?: 0
    val itemCount = adapter?.itemCount?: Int.MAX_VALUE

    return lastVisiblePosition > itemCount - 5
}