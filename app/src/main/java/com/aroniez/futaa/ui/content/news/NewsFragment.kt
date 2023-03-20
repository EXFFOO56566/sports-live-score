package com.aroniez.futaa.ui.content.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aroniez.futaa.R
import com.aroniez.futaa.api.NEWS_URL
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.NewsCallback
import com.aroniez.futaa.utils.hideLoadingProgress
import com.aroniez.futaa.utils.showLoadingProgress
import com.aroniez.futaa.utils.showMessageLayout
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.include_recyclerview_progressbar_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadGames()

        //.setOnRefreshListener { loadGames() }
    }

    private fun loadGames() {
        showLoadingProgress(baseNestedLayout)
        val callback = RetrofitAdapter.createAPI(NEWS_URL).news()
        callback.enqueue(object : Callback<NewsCallback> {
            override fun onFailure(call: Call<NewsCallback>, t: Throwable) {
                hideLoadingProgress(baseNestedLayout)
                showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
            }

            override fun onResponse(call: Call<NewsCallback>, response: Response<NewsCallback>) {
                hideLoadingProgress(baseNestedLayout)
                if (response.isSuccessful) {
                    val adapter = NewsAdapter(response.body()!!.articles, context!!)
                    baseRecyclerView.layoutManager = LinearLayoutManager(context)
                    baseRecyclerView.adapter = adapter
                } else {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                }
            }
        })
    }

}