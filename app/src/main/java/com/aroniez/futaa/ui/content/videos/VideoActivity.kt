package com.aroniez.futaa.ui.content.videos

import android.os.Bundle
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.aroniez.futaa.R
import com.aroniez.futaa.models.video.VideoHighlight
import com.aroniez.futaa.utils.loadMediumBannerAds
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.include_ads_layout.*


class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_PROGRESS)
        setContentView(R.layout.activity_video_detail)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val video = intent.getSerializableExtra("video") as VideoHighlight
        supportActionBar!!.title = video.title

        webView.webChromeClient = object : WebChromeClient() {}
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadData(video.embed!!, "text/html; charset=utf-8", "UTF-8")

        loadMediumBannerAds(this, advertLayout)

    }
}
