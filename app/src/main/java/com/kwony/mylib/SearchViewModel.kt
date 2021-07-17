package com.kwony.mylib

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwony.data.LibraryRepository
import com.kwony.data.Status
import com.kwony.data.vo.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository
): ViewModel() {

    val searchBooks = MutableLiveData<List<Book>>()

    val errorMessage = MutableLiveData<String>()

    private val pagingInfo: PagingInfo = PagingInfo()

    private var loadJob: Job? = null

    fun loadQuery(query: String) {
        if (loadJob?.isActive == true) return

        loadJob = viewModelScope.launch {
            val result = libraryRepository.loadSearch(query, 0)

            if (result.status == Status.SUCCESS) {
                pagingInfo.set(query)
                searchBooks.value = result.data?.books
            } else {
                errorMessage.value = result.message
            }
        }
    }


    fun loadQueryMore() {
        if (loadJob?.isActive == true) return

        loadJob = viewModelScope.launch {
            val result = libraryRepository.loadSearch(pagingInfo.query, pagingInfo.nextPage())

            if (result.status == Status.SUCCESS) {
                searchBooks.value = ArrayList<Book>().apply {
                    searchBooks.value?.let { addAll(it) }
                    result.data?.books?.let { addAll(it) }
                }.toList()
            } else {
                errorMessage.value = result.message
            }
        }
    }


    private data class PagingInfo(private var _query: String = "", private var _page: Int = 0) {
        val query get() = _query

        val page get() = _page

        fun nextPage(): Int {
            return ++_page
        }

        fun set(query: String) {
            _query = query
            _page = 0
        }
    }
}