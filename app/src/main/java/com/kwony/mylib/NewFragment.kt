package com.kwony.mylib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.kwony.mylib.databinding.FragmentNewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewFragment : BaseFragment<FragmentNewBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNewBinding = FragmentNewBinding::inflate

    private val requestManager by lazy {
        Glide.with(this)
    }

    private val newViewModel by lazy {
        ViewModelProvider(this).get(NewViewModel::class.java)
    }

    private val adapter by lazy {
        BookAdapter(requestManager)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        newViewModel.newBooks.observe(viewLifecycleOwner, {
            adapter.submitItems(it)
        })

        loadNew()
    }

    private fun loadNew() {
        newViewModel.loadNew()
    }
}