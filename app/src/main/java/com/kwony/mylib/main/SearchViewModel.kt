package com.kwony.mylib.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwony.data.LibraryRepository
import com.kwony.data.Status
import com.kwony.data.vo.Book
import com.kwony.data.vo.BookSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository
): ViewModel() {
    val searchBooks = MutableLiveData<List<Book>>()

    val searchHistory = MutableLiveData<List<BookSearch>>()

    val errorMessage = MutableLiveData<String>()

    private val pagingInfo: PagingInfo = PagingInfo()

    private var loadJob: Job? = null

    fun loadHistory() {
        viewModelScope.launch {
            libraryRepository.loadBookSearchHistory()
                .distinctUntilChanged()
                .collect { list ->
                    searchHistory.value = list ?: listOf()
                }
        }
    }

    fun loadQuery(query: String) {
        loadJob?.cancel()

        loadJob = viewModelScope.launch {
            val result = libraryRepository.loadSearch(query, 0)

            if (result.status == Status.SUCCESS) {
                pagingInfo.set(query)
                searchBooks.value = result.data?.books
            } else {
                errorMessage.value = result.message
            }

            libraryRepository.addBookSearch(query)
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

    fun deleteQueryHistory(bookSearch: BookSearch) {
        viewModelScope.launch {
            libraryRepository.removeBookSearch(bookSearch.query)
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