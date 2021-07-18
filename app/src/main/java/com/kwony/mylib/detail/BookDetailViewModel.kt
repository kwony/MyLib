package com.kwony.mylib.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwony.data.LibraryRepository
import com.kwony.data.vo.BookDetail
import com.kwony.data.vo.BookRelationType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
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

    val errorMessage = MutableLiveData<Throwable>()

    val isBookMarked = MutableLiveData<Boolean>()

    val bookMemo = MutableLiveData<String>()

    private var loadBookRelationJob: Job? = null

    private var loadBookMemoJob: Job? = null

    private var isbn13: Long = 0L

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorMessage.value = throwable
    }

    fun init(isbn13: Long) {
        this.isbn13 = isbn13

        viewModelScope.launch(exceptionHandler) {
            val resp = libraryRepository.loadBookDetail(isbn13)

            bookDetail.value = resp
        }

        loadBookRelationJob?.cancel()
        loadBookRelationJob = viewModelScope.launch(exceptionHandler) {
            libraryRepository.loadBookRelation(isbn13)
                .distinctUntilChanged()
                .collect { relation ->
                    isBookMarked.value = relation?.relationType == BookRelationType.BOOK_MARK
                }
        }

        loadBookMemoJob?.cancel()
        loadBookMemoJob = viewModelScope.launch(exceptionHandler) {
            libraryRepository.loadBookMemo(isbn13)
                .distinctUntilChanged()
                .collect { memo ->
                    bookMemo.value = memo?.memo ?: ""
                }
        }
    }

    fun shuffleBookMark() {
        viewModelScope.launch(exceptionHandler) {
            if (isBookMarked.value == true) {
                libraryRepository.removeBookmark(isbn13)
            } else {
                libraryRepository.addBookmark(isbn13)
            }
        }
    }

    fun saveMemo(memo: String) {
        viewModelScope.launch(exceptionHandler) {
            libraryRepository.addBookMemo(this@BookDetailViewModel.isbn13, memo)
        }
    }

    fun deleteMemo() {
        viewModelScope.launch(exceptionHandler) {
            libraryRepository.deleteBookMemo(this@BookDetailViewModel.isbn13)
        }
    }
}