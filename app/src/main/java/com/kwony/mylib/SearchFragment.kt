package com.kwony.mylib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kwony.mylib.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding = FragmentSearchBinding::inflate

    private val requestManager by lazy { Glide.with(this) }

    private val searchViewModel by lazy { ViewModelProvider(this).get(SearchViewModel::class.java) }

    private val adapter by lazy { BookAdapter(requestManager) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchDone.setOnClickListener {
            if (binding.searchText.text.toString().isBlank()) return@setOnClickListener

            searchViewModel.loadQuery(binding.searchText.text.toString())
        }

        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rv.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (recyclerView.needToLoadMoreLayoutManager()) {
                    searchViewModel.loadQueryMore()
                }
            }
        })

        searchViewModel.searchBooks.observe(viewLifecycleOwner, {
            adapter.submitItems(it)
        })

        searchViewModel.errorMessage.observe(viewLifecycleOwner, { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })
    }
}