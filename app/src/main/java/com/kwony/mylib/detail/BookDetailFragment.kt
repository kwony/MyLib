package com.kwony.mylib.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kwony.mylib.base.BaseFragment
import com.kwony.mylib.R
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

        initView()
        observe()
    }

    private fun initView() {
        binding.bookmark.setOnClickListener {
            bookDetailViewModel.shuffleBookMark()
        }

        binding.saveMemo.setOnClickListener {
            bookDetailViewModel.saveMemo(binding.editMemo.text.toString())
        }

        binding.deleteMemo.setOnClickListener {
            bookDetailViewModel.deleteMemo()
        }
    }

    private fun observe() {
        bookDetailViewModel.bookDetail.observe(viewLifecycleOwner, { bookDetail ->
            requestManager.load(bookDetail.image)
                    .into(binding.cover)

            binding.title.text = String.format("%s: %s", getString(R.string.book_detail_title), bookDetail.title)
            binding.subTitle.text = String.format("%s: %s", getString(R.string.book_detail_subtitle), bookDetail.subtitle)
            binding.authors.text = String.format("%s: %s", getString(R.string.book_detail_authors), bookDetail.authors)
            binding.publishers.text = String.format("%s: %s", getString(R.string.book_detail_publishers), bookDetail.publisher)
            binding.pages.text = String.format("%s: %s", getString(R.string.book_detail_pages), bookDetail.pages.toString())
            binding.year.text = String.format("%s: %s", getString(R.string.book_detail_year), bookDetail.year.toString())
            binding.rating.text = String.format("%s: %s", getString(R.string.book_detail_rating), bookDetail.rating.toString())
            binding.price.text = String.format("%s: %s", getString(R.string.book_detail_price), bookDetail.price)
            binding.desc.text = String.format("%s: %s", getString(R.string.book_detail_desc), bookDetail.desc)
            binding.isbn.text = String.format("%s: %s", getString(R.string.book_detail_isbn), bookDetail.isbn13)
            binding.url.text = String.format("%s: %s", getString(R.string.book_detail_url), bookDetail.url)
            binding.freeEbook.text = String.format("%s: %s", getString(R.string.book_detail_free_ebook), bookDetail.pdf?.freeEbook)
        })

        bookDetailViewModel.isBookMarked.observe(viewLifecycleOwner, { bookMarked ->
            val drawable = if (bookMarked) {
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_bookmark_added)
            } else {
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_bookmark_add)
            }
            binding.bookmark.setImageDrawable(drawable)
        })

        bookDetailViewModel.bookMemo.observe(viewLifecycleOwner, { memo ->
            binding.memo.text = String.format("%s: %s", getString(R.string.book_detail_memo), memo)
        })

        bookDetailViewModel.errorMessage.observe(viewLifecycleOwner, { throwable ->
            Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_SHORT).show()
        })

        bookDetailViewModel.init(isbn13)
    }
}