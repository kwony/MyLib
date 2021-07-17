package com.kwony.mylib.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.kwony.mylib.base.BaseFragment
import com.kwony.mylib.databinding.FragmentMainTabBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainTabFragment: BaseFragment<FragmentMainTabBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainTabBinding = FragmentMainTabBinding::inflate

    private val adapter by lazy {
        MainTabPagerAdapter(childFragmentManager, lifecycle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewpager2.adapter = adapter
        binding.viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpager2.isUserInputEnabled = false

        binding.newTab.setOnClickListener {
            binding.viewpager2.setCurrentItem(adapter.getPosition(TabType.NEW), false)
        }
        binding.searchTab.setOnClickListener {
            binding.viewpager2.setCurrentItem(adapter.getPosition(TabType.SEARCH), false)
        }
        binding.bookmarkTab.setOnClickListener {
            binding.viewpager2.setCurrentItem(adapter.getPosition(TabType.BOOK_MARK), false)
        }

        binding.viewpager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when (adapter.getType(position)) {
                    TabType.NEW -> {
                        binding.newTab.setTextColor(0xff000000.toInt())
                        binding.searchTab.setTextColor(0xffd8d8d8.toInt())
                        binding.bookmarkTab.setTextColor(0xffd8d8d8.toInt())
                    }
                    TabType.BOOK_MARK -> {
                        binding.bookmarkTab.setTextColor(0xff000000.toInt())
                        binding.searchTab.setTextColor(0xffd8d8d8.toInt())
                        binding.newTab.setTextColor(0xffd8d8d8.toInt())
                    }
                    TabType.SEARCH -> {
                        binding.searchTab.setTextColor(0xff000000.toInt())
                        binding.bookmarkTab.setTextColor(0xffd8d8d8.toInt())
                        binding.newTab.setTextColor(0xffd8d8d8.toInt())
                    }
                }
            }
        })
    }

    private class MainTabPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
        private val items = listOf(TabType.NEW, TabType.SEARCH, TabType.BOOK_MARK)

        override fun getItemCount(): Int = items.size

        override fun createFragment(position: Int): Fragment {
            return when (items[position]) {
                TabType.NEW -> NewFragment()
                TabType.BOOK_MARK -> BookMarkFragment()
                TabType.SEARCH -> SearchFragment()
            }
        }

        fun getPosition(tabType: TabType) = items.indexOfFirst { it == tabType }

        fun getType(position: Int) = items[position]
    }

    enum class TabType {
        NEW, SEARCH, BOOK_MARK
    }
}