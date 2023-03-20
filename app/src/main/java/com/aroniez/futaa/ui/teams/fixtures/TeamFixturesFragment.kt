package com.aroniez.futaa.ui.teams.fixtures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aroniez.futaa.R
import com.aroniez.futaa.api.BASE_URL
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.TeamCallback
import com.aroniez.futaa.events.OnTeamFixturesLoaded
import com.aroniez.futaa.ui.teams.TeamDetailActivity
import com.aroniez.futaa.utils.*
import kotlinx.android.synthetic.main.fragment_viewpager_with_loading.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TeamFixturesFragment : Fragment() {
    private lateinit var onTeamFixturesLoaded: OnTeamFixturesLoaded

    fun setOnTeamFixturesLoaded(onTeamFixturesLoaded: OnTeamFixturesLoaded) {
        this.onTeamFixturesLoaded = onTeamFixturesLoaded
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.include_recyclerview_progressbar_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchTeamDetailFromAPI((context as TeamDetailActivity).getTeam().id)
    }

    private fun fetchTeamDetailFromAPI(teamId: Long) {
        showLoadingProgress(baseNestedLayout)
        val callback = RetrofitAdapter.createAPI(BASE_URL).teamFixturesById(teamId)
        callback.enqueue(viewLifecycleOwner, object : Callback<TeamCallback> {
            override fun onFailure(call: Call<TeamCallback>, t: Throwable) {
                if (viewpagerLoading != null) {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                }
            }

            override fun onResponse(call: Call<TeamCallback>, response: Response<TeamCallback>) {
                hideLoadingProgress(baseNestedLayout)
                if (response.isSuccessful) {
                    onTeamFixturesLoaded.onFixtureLoaded(response.body()!!.data.upcoming.data[0], true)
                    displayFixturesLite(response.body()!!.data.upcoming.data, baseNestedLayout, context!!, teamId)

                } else {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                }
            }
        })
    }
}
