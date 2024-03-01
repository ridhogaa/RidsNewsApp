package com.ergea.ridsnewsapp.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.ergea.ridsnewsapp.databinding.ActivityDetailBinding
import com.ergea.ridsnewsapp.model.News
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private val news by lazy {
        intent?.getParcelableExtra<News>(EXTRA_ARTICLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindViews()
    }

    private fun bindViews() = binding.run {
        icBack.setOnClickListener { onBackPressed() }
        webView.apply {
            settings.loadsImagesAutomatically = true
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            webViewClient = WebViewClient()
            loadUrl(news?.url.orEmpty())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val EXTRA_ARTICLE = "EXTRA_ARTICLE"

        fun startActivity(
            context: Context,
            news: Parcelable
        ) {
            Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_ARTICLE, news)
            }.run { context.startActivity(this) }
        }
    }
}