package com.kwony.mylib.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kwony.mylib.base.BaseFragment
import com.kwony.mylib.databinding.FragmentBookMarkBinding
import com.kwony.mylib.detail.BookDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookMarkFragment : BaseFragment<FragmentBookMarkBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBookMarkBinding = FragmentBookMarkBinding::inflate

    private val requestManager by lazy { Glide.with(this) }

    private val bookMarkViewModel by lazy { ViewModelProvider(this).get(BookMarkViewModel::class.java) }

    private val adapter by lazy {
        BookMarkAdapter(requestManager, {
            BookDetailActivity.startActivity(requireContext(), it.isbn13)
        }, {
            bookMarkViewModel.removeBookMark(it.isbn13)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observe()
    }

    private fun initView() {
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.addedSort.setOnClickListener {
            bookMarkViewModel.setSortOrder(BookMarkViewModel.SortType.ADDED)
        }

        binding.publishSort.setOnClickListener {
            bookMarkViewModel.setSortOrder(BookMarkViewModel.SortType.PUBLISH)
        }

        binding.priceSort.setOnClickListener {
            bookMarkViewModel.setSortOrder(BookMarkViewModel.SortType.PRICE)
        }
    }

    private fun observe() {
        bookMarkViewModel.bookmarkBooks.observe(viewLifecycleOwner, { list ->
            adapter.submitItems(list)
        })

        bookMarkViewModel.sortType.observe(viewLifecycleOwner, { sortType ->
            when (sortType) {
                BookMarkViewModel.SortType.PUBLISH -> {
                    setSortView(binding.publishSort, listOf(binding.addedSort, binding.priceSort))
                }
                BookMarkViewModel.SortType.ADDED -> {
                    setSortView(binding.addedSort, listOf(binding.publishSort, binding.priceSort))
                }
                BookMarkViewModel.SortType.PRICE -> {
                    setSortView(binding.priceSort, listOf(binding.publishSort, binding.addedSort))
                }
                else -> {}
            }
        })

        bookMarkViewModel.loadBookmark()
    }

    private fun setSortView(selectedView: TextView, unselecteds: List<TextView>) {
        selectedView.setTextColor(0xff000000.toInt())

        unselecteds.forEach { it.setTextColor(0xffd8d8d8.toInt()) }
    }
}