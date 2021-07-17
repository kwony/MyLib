package com.kwony.mylib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kwony.mylib.databinding.FragmentBookDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailFragment : BaseFragment<FragmentBookDetailBinding>() {
    companion object {
        private const val KEY_ISBN13 = "key_isbn13"

        @JvmStatic
        fun newInstance(isbn13: Long): BookDetailFragment {
            return Bundle().apply {
                putLong(KEY_ISBN13, isbn13)
            }.run {
                BookDetailFragment().apply { arguments = this@run }
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBookDetailBinding = FragmentBookDetailBinding::inflate

    private val requestManager by lazy { Glide.with(this) }

    private val bookDetailViewModel by lazy { ViewModelProvider(this).get(BookDetailViewModel::class.java) }

    private val isbn13 by lazy { arguments?.getLong(KEY_ISBN13) ?: 0L }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookDetailViewModel.bookDetail.observe(viewLifecycleOwner, { bookDetail ->
            requestManager.load(bookDetail.image)
                .into(binding.cover)

            binding.title.text = bookDetail.title
            binding.subTitle.text = bookDetail.subtitle
            binding.authors.text = bookDetail.authors
            binding.publishers.text = bookDetail.publisher
            binding.desc.text = bookDetail.desc
        })

        bookDetailViewModel.isBookMarked.observe(viewLifecycleOwner, { bookMarked ->

        })

        bookDetailViewModel.init(isbn13)
    }
}