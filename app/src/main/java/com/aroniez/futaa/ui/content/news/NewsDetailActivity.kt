package com.aroniez.futaa.ui.content.news

import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aroniez.futaa.R
import com.aroniez.futaa.api.NEWS_URL
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.SingleNewsCallback
import com.aroniez.futaa.utils.DateTimeUtil
import com.google.android.gms.ads.AdRequest
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_news_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        Picasso.get().load(intent.getStringExtra("image")).into(newsImage)
        newTitle.text = intent.getStringExtra("title")
        newsTime.text = DateTimeUtil.getRelativeTime(intent.getStringExtra("timestamp")!!.toLong())

        loadNews()

        val request = AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("AC98C820A50B4AD8A2106EDE96FB87D4")
                .build()
        adView.loadAd(request)

    }

    private fun loadNews() {
        val callback = RetrofitAdapter.createAPI(NEWS_URL).singleNews(intent.getStringExtra("id")!!, intent.getStringExtra("slug")!!)
        callback.enqueue(object : Callback<SingleNewsCallback> {
            override fun onFailure(call: Call<SingleNewsCallback>, t: Throwable) {
                Toast.makeText(this@NewsDetailActivity, "Error loading news!", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<SingleNewsCallback>, response: Response<SingleNewsCallback>) {
                if (response.isSuccessful) {
                    newsContent.text = Html.fromHtml(response.body()!!.article.content)
                    Picasso.get().load(response.body()!!.article.cdn_image).into(newsImage)
                } else {
                    Toast.makeText(this@NewsDetailActivity, "Error loading news!", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}
