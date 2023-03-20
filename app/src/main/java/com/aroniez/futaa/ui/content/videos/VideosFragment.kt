package com.aroniez.futaa.ui.content.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aroniez.futaa.R
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.VIDEO_URL
import com.aroniez.futaa.models.video.VideoHighlight
import com.aroniez.futaa.utils.hideLoadingProgress
import com.aroniez.futaa.utils.showLoadingProgress
import com.aroniez.futaa.utils.showMessageLayout
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideosFragment : Fragment() {

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
        val callback = RetrofitAdapter.createAPI(VIDEO_URL).videos()
        callback.enqueue(object : Callback<ArrayList<VideoHighlight>> {
            override fun onFailure(call: Call<ArrayList<VideoHighlight>>, t: Throwable) {
                hideLoadingProgress(baseNestedLayout)
                showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
            }

            override fun onResponse(call: Call<ArrayList<VideoHighlight>>, response: Response<ArrayList<VideoHighlight>>) {
                hideLoadingProgress(baseNestedLayout)
                if (response.isSuccessful) {
                    val adapter = VideosAdapter(response.body()!!, context!!)
                    baseRecyclerView.layoutManager = LinearLayoutManager(context)
                    baseRecyclerView.adapter = adapter
                } else {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                }
            }
        })
    }

}