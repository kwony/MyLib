package com.kwony.mylib

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwony.data.LibraryRepository
import com.kwony.data.vo.Book
import dagger.hilt.android.lifecycle.HiltViewModel
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
    val bookmarkBooks = MutableLiveData<List<Book>>()

    val errorMessage = MutableLiveData<String>()

    fun loadBookmark() {
        viewModelScope.launch {
            libraryRepository.loadBookMark()
                .distinctUntilChanged()
                .collect { list ->
                    list?.let {
                        bookmarkBooks.value = it
                    }
                }
        }
    }
}