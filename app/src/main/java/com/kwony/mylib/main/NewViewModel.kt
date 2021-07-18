package com.kwony.mylib.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwony.data.LibraryRepository
import com.kwony.data.vo.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class NewViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository
): ViewModel() {

    val newBooks = MutableLiveData<List<Book>>()

    val errorMessage = MutableLiveData<Throwable>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorMessage.value = throwable
    }

    fun loadNew() {
        viewModelScope.launch(exceptionHandler) {
            val resp = libraryRepository.loadNew()

            if (resp.error == 0) {
                newBooks.value = resp.books
            } else {
                throw Exception("Unknown Exception")
            }
        }
    }
}