package com.aroniez.futaa.ui.browser

import android.os.Bundle
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_in_browser.*


class InBrowserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_PROGRESS)
        setContentView(com.aroniez.futaa.R.layout.activity_in_browser)
        window.setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON)

        setSupportActionBar(toolbar)
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                title = "Loading..."
                setProgress(progress * 100) //Make the bar disappear after URL is loaded

                // Return the app name after finish loading
                if (progress == 100)
                    title = "Cricket"
            }
        }
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(intent.getStringExtra("url")!!)
    }

}