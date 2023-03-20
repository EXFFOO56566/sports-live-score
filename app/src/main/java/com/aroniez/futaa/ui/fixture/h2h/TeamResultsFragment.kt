package com.aroniez.futaa.ui.fixture.h2h

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aroniez.futaa.R
import com.aroniez.futaa.api.BASE_URL
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.TeamCallback
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.utils.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TeamResultsFragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.include_recyclerview_progressbar_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchTeamDetailFromAPI()
    }

    private fun fetchTeamDetailFromAPI() {
        val teamId = arguments!!.getLong("team_id")
        val callback = RetrofitAdapter.createAPI(BASE_URL).teamResultsById(teamId)
        showLoadingProgress(baseNestedLayout)
        callback.enqueue(viewLifecycleOwner, object : Callback<TeamCallback> {
            override fun onFailure(call: Call<TeamCallback>, t: Throwable) {
                if (context != null && baseNestedLayout != null) {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                }
            }

            override fun onResponse(call: Call<TeamCallback>, response: Response<TeamCallback>) {
                hideLoadingProgress(baseNestedLayout)
                if (response.isSuccessful) {
                    val allFixtures: ArrayList<Fixture> = arrayListOf()
                    allFixtures.addAll(response.body()!!.data.localResults.data)
                    allFixtures.addAll(response.body()!!.data.visitorResults.data)
                    displayFixturesLite(allFixtures, baseNestedLayout, context!!, teamId)
                } else {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                }
            }

        })
    }
}
