package com.aroniez.futaa.ui.fixture.commentary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aroniez.futaa.R
import com.aroniez.futaa.api.BASE_URL
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.CommentariesCallback
import com.aroniez.futaa.ui.fixture.MatchDetailActivity
import com.aroniez.futaa.utils.hideLoadingProgress
import com.aroniez.futaa.utils.showLoadingProgress
import com.aroniez.futaa.utils.showMessageLayout
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentariesFragment : Fragment() {

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
        val match = (context as MatchDetailActivity).getMatchObject()
        val callback = RetrofitAdapter.createAPI(BASE_URL).commentary(match!!.id)
        callback.enqueue(object : Callback<CommentariesCallback> {
            override fun onFailure(call: Call<CommentariesCallback>, t: Throwable) {
                hideLoadingProgress(baseNestedLayout)
                showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
            }

            override fun onResponse(call: Call<CommentariesCallback>, response: Response<CommentariesCallback>) {
                hideLoadingProgress(baseNestedLayout)
                if (response.isSuccessful) {
                    if (baseRecyclerView != null) {
                        val adapter = CommentariesAdapter(response.body()!!.data, context!!)
                        baseRecyclerView.layoutManager = LinearLayoutManager(context)
                        baseRecyclerView.adapter = adapter
                    }
                } else {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                }
            }
        })
    }

}