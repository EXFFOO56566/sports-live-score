package com.aroniez.futaa.ui.season.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aroniez.futaa.R
import com.aroniez.futaa.api.*
import com.aroniez.futaa.api.callbacks.SeasonMatchesCallback
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.ui.season.LeagueDetailActivity
import com.aroniez.futaa.utils.*
import kotlinx.android.synthetic.main.include_load_more_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Base class that loads season matches. This includes both results and upcoming
 * */
abstract class SeasonMatchesBaseFragment() : Fragment() {
    private var currentPage = 1
    private var matches: ArrayList<Fixture> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.include_recyclerview_progressbar_layout, container, false)
    }

    abstract fun getMatchesType(): String //either results or upcoming

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchDataFromServer(currentPage, false)

        baseLoadMoreLayout.setOnClickListener { fetchDataFromServer(currentPage, true) }
    }

    private fun fetchDataFromServer(page: Int, isLoadingMore: Boolean) {
        currentPage += 1
        val seasonId = (context as LeagueDetailActivity).getSeasonId()
        val url = if (getMatchesType() == "results") {
            "seasons/$seasonId/?api_token=$API_KEY&include=results:limit($queryLimit|$page):order(round_id|desc),$results,round"
        } else {
            "seasons/$seasonId/?api_token=$API_KEY&include=upcoming:limit($queryLimit|$page),$upcoming,round"
        }

        if (isLoadingMore) {
            showLoadMoreProgress(baseNestedLayout)
        } else {
            showLoadingProgress(baseNestedLayout)
        }
        val callback = RetrofitAdapter.createAPI(BASE_URL).seasonMatches(url)
        callback.enqueue(viewLifecycleOwner, object : Callback<SeasonMatchesCallback> {
            override fun onFailure(call: Call<SeasonMatchesCallback>, t: Throwable) {
                if (isLoadingMore) {
                    showLoadMoreMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                } else {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                }
            }

            override fun onResponse(call: Call<SeasonMatchesCallback>, response: Response<SeasonMatchesCallback>) {
                if (isLoadingMore) {
                    hideLoadMoreProgress(baseNestedLayout)
                } else {
                    hideLoadingProgress(baseNestedLayout)
                }
                if (response.isSuccessful) {
                    val fixtures = if (getMatchesType() == "results") {
                        response.body()!!.data.results.data.sortedByDescending { it.id }
                    } else {
                        response.body()!!.data.upcoming.data
                    }
                    matches.addAll(fixtures)
                    if (response.body()!!.data.round != null) {
                        displayFixturesGroupedLite(matches, baseNestedLayout, context!!, response.body()!!.data.round.data)
                    }
                } else {
                    if (isLoadingMore) {
                        showLoadMoreMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                    } else {
                        showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                    }
                }
            }
        })
    }
}