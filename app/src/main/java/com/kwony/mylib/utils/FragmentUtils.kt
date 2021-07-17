package com.kwony.mylib.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentUtils {
    companion object {
        @JvmStatic
        fun hasFragment(fragmentManager: FragmentManager, tag: String?): Boolean {
            return fragmentManager.findFragmentByTag(tag) != null
        }

        @JvmStatic
        fun replaceFragmentIfNotExists(
            fragmentManager: FragmentManager, @IdRes contentId: Int,
            tag: String?,
            fragment: Fragment,
            now: Boolean
        ): Boolean {
            if (hasFragment(fragmentManager, tag)) {
                return false
            }
            val fragmentTransaction = fragmentManager
                .beginTransaction()
                .replace(contentId, fragment, tag)
                .setPrimaryNavigationFragment(fragment)
            if (now) {
                fragmentTransaction.commitNowAllowingStateLoss()
            } else {
                fragmentTransaction.commitAllowingStateLoss()
            }
            return true
        }
    }
}