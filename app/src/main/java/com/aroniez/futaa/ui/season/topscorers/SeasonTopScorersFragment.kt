package com.aroniez.futaa.ui.season.topscorers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aroniez.futaa.R
import com.aroniez.futaa.api.BASE_URL
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.SeasonTopScorersCallback
import com.aroniez.futaa.ui.season.LeagueDetailActivity
import com.aroniez.futaa.utils.hideLoadingProgress
import com.aroniez.futaa.utils.showLoadingProgress
import com.aroniez.futaa.utils.showMessageLayout
import kotlinx.android.synthetic.main.fragment_viewpager_with_loading.*
import kotlinx.android.synthetic.main.include_viewpager_tabs_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SeasonTopScorersFragment : Fragment() {
    private var callback: Call<SeasonTopScorersCallback>? = null
    override fun onDestroy() {
        super.onDestroy()
        if (callback != null) {
            callback!!.cancel()
        }
    }

    override fun onStop() {
        super.onStop()
        if (callback != null) {
            callback!!.cancel()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_viewpager_with_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coverage = (context as LeagueDetailActivity).getSeasonCoverage()
        if (coverage != null) {
            if (coverage.topscorer_assists && coverage.topscorer_cards && coverage.topscorer_goals) {
                fetchDataFromServer()
            } else {
                showMessageLayout("Top scorers not available at the moment", viewpagerLoading)
            }
        }
    }

    private fun fetchDataFromServer() {
        showLoadingProgress(viewpagerLoading)
        callback = RetrofitAdapter.createAPI(BASE_URL).seasonTopPlayers((context as LeagueDetailActivity).getSeasonId())
        callback!!.enqueue(object : Callback<SeasonTopScorersCallback> {
            override fun onFailure(call: Call<SeasonTopScorersCallback>, t: Throwable) {
                if (viewpagerLoading != null) {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), viewpagerLoading)
                }
            }

            override fun onResponse(call: Call<SeasonTopScorersCallback>, response: Response<SeasonTopScorersCallback>) {
                hideLoadingProgress(viewpagerLoading)
                if (response.isSuccessful) {
                    val topScorersCallback = response.body()!!.data
                    if (viewpager != null) {
                        viewpager.adapter = SeasonTopScorersFragmentsAdapter(childFragmentManager, topScorersCallback)
                        tabLayout.setupWithViewPager(viewpager)
                        viewpager.offscreenPageLimit = 2
                        viewpagerTabsLayout.visibility = View.VISIBLE
                    }
                } else {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), viewpagerLoading)
                }
            }
        })
    }

}
