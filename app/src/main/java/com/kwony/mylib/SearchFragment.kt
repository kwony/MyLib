package com.kwony.mylib

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.kwony.mylib.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding = FragmentSearchBinding::inflate

    private val requestManager by lazy { Glide.with(this) }

    private val searchViewModel by lazy { ViewModelProvider(this).get(SearchViewModel::class.java) }

    private val adapter by lazy { BookAdapter(requestManager) }






}