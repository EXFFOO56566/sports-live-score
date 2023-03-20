package com.aroniez.futaa.ui.season.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aroniez.futaa.R
import com.aroniez.futaa.api.BASE_URL
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.SeasonFixturesCallback
import com.aroniez.futaa.ui.season.LeagueDetailActivity
import com.aroniez.futaa.utils.displayFixtures
import com.aroniez.futaa.utils.hideLoadingProgress
import com.aroniez.futaa.utils.showLoadingProgress
import com.aroniez.futaa.utils.showMessageLayout
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SeasonFixtureMatchesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.include_recyclerview_progressbar_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadCompetitions()

    }


    private fun loadCompetitions() {
        showLoadingProgress(baseNestedLayout)
        val callback = RetrofitAdapter.createAPI(BASE_URL).seasonFixtures((context as LeagueDetailActivity).getSeasonId())
        callback.enqueue(object : Callback<SeasonFixturesCallback> {
            override fun onFailure(call: Call<SeasonFixturesCallback>, t: Throwable) {
                hideLoadingProgress(baseNestedLayout)
                showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
            }

            override fun onResponse(call: Call<SeasonFixturesCallback>, response: Response<SeasonFixturesCallback>) {
                hideLoadingProgress(baseNestedLayout)
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val rawMatches = response.body()!!.data.fixtures.data
                        if (rawMatches.isNotEmpty()) {
                            displayFixtures(rawMatches, baseNestedLayout, context!!, false)
                        } else {
                            showMessageLayout("No matches found", baseNestedLayout)
                        }
                    } else {
                        showMessageLayout("No matches found", baseNestedLayout)
                    }
                } else {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                }
            }
        })
    }
}