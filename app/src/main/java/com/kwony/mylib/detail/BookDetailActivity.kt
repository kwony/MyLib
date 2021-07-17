package com.kwony.mylib.detail

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kwony.mylib.utils.FragmentUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailActivity : AppCompatActivity() {
    companion object {
        private const val KEY_ISBN13 = "KEY_ISBN"

        @JvmStatic
        fun startActivity(context: Context, isbn13: Long) {
            Intent(context, BookDetailActivity::class.java).apply {
                putExtra(KEY_ISBN13, isbn13)
            }.let {
                context.startActivity(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isbn13 = intent.getLongExtra(KEY_ISBN13, 0L)

        FragmentUtils.replaceFragmentIfNotExists(
            supportFragmentManager,
            R.id.content,
            "bookdetailfragment",
            BookDetailFragment.newInstance(isbn13),
            true
        )
    }
}