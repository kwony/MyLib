package com.kwony.mylib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.kwony.mylib.databinding.FragmentMainTabBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainTabFragment: BaseFragment<FragmentMainTabBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainTabBinding = FragmentMainTabBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewpager2.adapter = MainTabPagerAdapter(childFragmentManager, lifecycle)
        binding.viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
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
    }

    enum class TabType {
        NEW, SEARCH, BOOK_MARK
    }
}