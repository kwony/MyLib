package com.kwony.mylib

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.kwony.data.vo.Book
import com.kwony.mylib.databinding.ViewholderBookBinding

class BookAdapter(
        private val requestManager: RequestManager
) : SimpleAdapter<Book, BookAdapter.BookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ViewholderBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(items[position], requestManager)
    }

    class BookViewHolder(private val binding: ViewholderBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book, requestManager: RequestManager) {
            binding.bookTitle.text = book.title
            binding.bookSubtitle.text = book.subTitle

            requestManager.load(book.image)
                    .into(binding.bookCover)
        }
    }
}

