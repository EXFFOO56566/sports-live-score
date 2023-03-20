package com.aroniez.futaa.ui.fixture.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aroniez.futaa.R
import com.aroniez.futaa.ui.fixture.MatchDetailActivity
import com.aroniez.futaa.utils.loadMediumBannerAds
import kotlinx.android.synthetic.main.fragment_match_statistics.*
import kotlinx.android.synthetic.main.include_ads_layout.*

class MatchStatisticsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()

        loadMediumBannerAds(context!!, advertLayout)

    }


    private fun loadData() {
        val match = (context as MatchDetailActivity).getMatchObject()
        if (match != null) {
            if (match.stats != null) {
                if (match.stats.data.isNotEmpty()) {
                    //show stats
                } else {
                    showMessageLayout("No match statistics at the moment")
                }
            } else {
                showMessageLayout("No match statistics at the moment")
            }
        } else {
            showMessageLayout("Error while fetching match statistics")
        }
    }

    private fun showMessageLayout(error: String) {
        message.visibility = View.VISIBLE
        message.text = error
    }
}