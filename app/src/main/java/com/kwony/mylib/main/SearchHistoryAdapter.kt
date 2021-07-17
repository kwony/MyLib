package com.kwony.mylib.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kwony.data.vo.BookSearch
import com.kwony.mylib.common.SimpleAdapter
import com.kwony.mylib.databinding.ViewholderBookSearchBinding

class SearchHistoryAdapter(
    private val searchClick: (bookSearch: BookSearch) -> Unit,
    private val deleteClick: (bookSearch: BookSearch) -> Unit,
) : SimpleAdapter<BookSearch, SearchHistoryAdapter.BookSearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSearchViewHolder {
        val binding = ViewholderBookSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookSearchViewHolder, position: Int) {
        holder.bind(items[position], searchClick, deleteClick)
    }

    class BookSearchViewHolder(private val binding: ViewholderBookSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bookSearch: BookSearch, itemClick: (bookSearch: BookSearch) -> Unit, deleteClick: (bookSearch: BookSearch) -> Unit) {
            binding.query.text = bookSearch.query

            binding.query.setOnClickListener { itemClick.invoke(bookSearch) }

            binding.delete.setOnClickListener { deleteClick.invoke(bookSearch) }
        }
    }
}