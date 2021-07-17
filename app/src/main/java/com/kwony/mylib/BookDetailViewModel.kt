package com.kwony.mylib

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwony.data.LibraryRepository
import com.kwony.data.Status
import com.kwony.data.vo.BookDetail
import com.kwony.data.vo.BookRelationType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository
): ViewModel() {
    val bookDetail = MutableLiveData<BookDetail>()

    val errorMessage = MutableLiveData<String>()

    val isBookMarked = MutableLiveData<Boolean>()

    private var loadBookRelationJob: Job? = null

    private var isbn13: Long = 0L

    fun init(isbn13: Long) {
        this.isbn13 = isbn13

        viewModelScope.launch {
            val resource = libraryRepository.loadBookDetail(isbn13)

            if (resource.status == Status.SUCCESS) {
                bookDetail.value = resource.data
            } else {
                errorMessage.value = resource.message
            }
        }

        loadBookRelationJob?.cancel()
        loadBookRelationJob = viewModelScope.launch {
            libraryRepository.loadBookRelation(isbn13)
                .distinctUntilChanged()
                .collect { relation ->
                    isBookMarked.value = relation?.relationType == BookRelationType.BOOK_MARK
                }
        }
    }

    fun cancelBookMark() {
        if (isBookMarked.value == false) return
        viewModelScope.launch {
            libraryRepository.removeBookmark(isbn13)
        }
    }

    fun addBookMark() {
        if (isBookMarked.value == true) return
        viewModelScope.launch {
            libraryRepository.addBookmark(isbn13)
        }
    }
}