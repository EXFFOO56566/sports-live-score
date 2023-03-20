package com.aroniez.futaa.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aroniez.futaa.R
import com.aroniez.futaa.api.BASE_URL
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.StandingsCallback
import com.aroniez.futaa.utils.enqueue
import com.aroniez.futaa.utils.hideLoadingProgress
import com.aroniez.futaa.utils.showLoadingProgress
import com.aroniez.futaa.utils.showMessageLayout
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LeagueStandingsFragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_standings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadCompetitions()
    }


    private fun loadCompetitions() {
        val seasonId = arguments!!.getLong("season_id")
        val homeId = arguments!!.getLong("home_id")
        val awayId = arguments!!.getLong("away_id")
        showLoadingProgress(baseNestedLayout)
        val call = RetrofitAdapter.createAPI(BASE_URL).standings(seasonId)
        call.enqueue(viewLifecycleOwner, object : Callback<StandingsCallback> {
            override fun onFailure(call: Call<StandingsCallback>, t: Throwable) {
                if (baseNestedLayout != null) {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                }
            }

            override fun onResponse(call: Call<StandingsCallback>, response: Response<StandingsCallback>) {
                hideLoadingProgress(baseNestedLayout)
                if (response.isSuccessful) {
                    if (response.body()!!.data.size > 0) {
                        if (baseRecyclerView != null) {
                            val adapter = StandingsAdapter(response.body()!!.data, context!!, homeId, awayId)
                            baseRecyclerView.layoutManager = LinearLayoutManager(context)
                            baseRecyclerView.adapter = adapter
                        }
                    } else {
                        showMessageLayout("Standings data not available", baseNestedLayout)
                    }
                } else {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                }
            }
        })
    }


}