package com.kwony.mylib

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.kwony.data.vo.Book
import com.kwony.data.vo.BookDetail
import com.kwony.mylib.databinding.ViewholderBookBinding
import com.kwony.mylib.databinding.ViewholderBookDetailBinding

class BookMarkAdapter(
        private val requestManager: RequestManager,
        private val bookClickListener: (book: BookDetail) -> Unit,
        private val deleteClickListener: (book: BookDetail) -> Unit
) : SimpleAdapter<BookDetail, BookMarkAdapter.BookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ViewholderBookDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(items[position], requestManager, bookClickListener, deleteClickListener)
    }

    class BookViewHolder(private val binding: ViewholderBookDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookDetail, requestManager: RequestManager, itemClick: (book: BookDetail) -> Unit, deleteClick: (book: BookDetail) -> Unit) {
            binding.bookTitle.text = book.title
            binding.bookSubtitle.text = book.subtitle
            binding.bookPrice.text = book.price
            binding.bookRating.text = book.rating.toString()
            binding.bookPages.text = book.pages.toString()

            binding.root.setOnClickListener {
                itemClick.invoke(book)
            }

            binding.delete.setOnClickListener {
                deleteClick.invoke(book)
            }

            requestManager.load(book.image)
                    .into(binding.bookCover)
        }
    }
}

