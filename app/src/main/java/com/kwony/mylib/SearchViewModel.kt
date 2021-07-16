package com.kwony.mylib

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kwony.data.LibraryRepository
import com.kwony.data.vo.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository
): ViewModel() {

    val searchResult = MutableLiveData<List<Book>>()



}