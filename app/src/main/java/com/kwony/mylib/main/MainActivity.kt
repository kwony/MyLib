package com.kwony.mylib.main

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kwony.mylib.utils.FragmentUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FragmentUtils.replaceFragmentIfNotExists(
            supportFragmentManager,
            R.id.content,
            "maintabfragment",
            MainTabFragment(),
            true
        )
    }
}