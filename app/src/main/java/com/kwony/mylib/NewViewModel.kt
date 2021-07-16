package com.kwony.mylib

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwony.data.LibraryRepository
import com.kwony.data.vo.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository
): ViewModel() {

    val newBooks = MutableLiveData<List<Book>>()

    fun loadNew() {
        viewModelScope.launch {
            val resource = libraryRepository.loadNew()

            newBooks.value = resource.data?.books
        }
    }
}