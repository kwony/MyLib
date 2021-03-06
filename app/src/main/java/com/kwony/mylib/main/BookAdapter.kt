package com.kwony.mylib.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.kwony.data.vo.Book
import com.kwony.mylib.common.SimpleAdapter
import com.kwony.mylib.databinding.ViewholderBookBinding

class BookAdapter(
        private val requestManager: RequestManager,
        private val bookClickListener: (book: Book) -> Unit
) : SimpleAdapter<Book, BookAdapter.BookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ViewholderBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(items[position], requestManager, bookClickListener)
    }

    class BookViewHolder(private val binding: ViewholderBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book, requestManager: RequestManager, itemClick: (book: Book) -> Unit) {
            binding.bookTitle.text = book.title
            binding.bookSubtitle.visibility = if (book.subtitle?.isBlank() == true) {
                View.GONE
            } else {
                View.VISIBLE
            }
            binding.bookSubtitle.text = book.subtitle
            binding.bookPrice.text = book.price
            binding.bookIsbn.text = book.isbn13.toString()
            binding.bookUrl.text = book.url

            binding.root.setOnClickListener {
                itemClick.invoke(book)
            }

            requestManager.load(book.image)
                    .into(binding.bookCover)
        }
    }
}

