package com.kwony.mylib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kwony.mylib.databinding.FragmentBookMarkBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookMarkFragment : BaseFragment<FragmentBookMarkBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBookMarkBinding = FragmentBookMarkBinding::inflate

    private val requestManager by lazy { Glide.with(this) }

    private val bookMarkViewModel by lazy { ViewModelProvider(this).get(BookMarkViewModel::class.java) }

    private val adapter by lazy { BookAdapter(requestManager) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        bookMarkViewModel.bookmarkBooks.observe(viewLifecycleOwner, {
            adapter.submitItems(it)
        })

        bookMarkViewModel.loadBookmark()
    }
}