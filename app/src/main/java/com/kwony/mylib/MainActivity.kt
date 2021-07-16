package com.kwony.mylib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FragmentUtils.replaceFragmentIfNotExists(
            supportFragmentManager,
            android.R.id.content,
            "maintabfragment",
            MainTabFragment(),
            true
        )
    }
}