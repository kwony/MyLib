package com.kwony.mylib.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwony.data.LibraryRepository
import com.kwony.data.vo.BookDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
        private val libraryRepository: LibraryRepository
) : ViewModel() {
    val bookmarkBooks = MutableLiveData<List<BookDetail>>()

    val sortType = MutableLiveData(SortType.ADDED)

    private var originalOrder: List<BookDetail> = listOf()


    fun loadBookmark() {
        viewModelScope.launch {
            libraryRepository.loadBookMark()
                .distinctUntilChanged()
                .collect { list ->
                    list?.let {
                        originalOrder = it
                        bookmarkBooks.value = sortBookList(it, sortType.value!!)
                    }
                }
        }
    }

    fun setSortOrder(sortType: SortType) {
        viewModelScope.launch {
            bookmarkBooks.value?.let {
                bookmarkBooks.value = sortBookList(it, sortType)
            }
            this@BookMarkViewModel.sortType.value = sortType
        }
    }

    fun removeBookMark(isbn13: Long) {
        viewModelScope.launch {
            libraryRepository.removeBookmark(isbn13)
        }
    }

    private suspend fun sortBookList(list: List<BookDetail>, sortType: SortType): List<BookDetail> = withContext(Dispatchers.IO) {
        return@withContext when (sortType) {
            SortType.ADDED -> originalOrder
            SortType.PRICE -> list.sortedBy { it.price.substring(1, it.price.length).toFloat() }
            SortType.PUBLISH -> list.sortedBy { it.year }
        }
    }

    enum class SortType {
        ADDED, PUBLISH, PRICE
    }
}