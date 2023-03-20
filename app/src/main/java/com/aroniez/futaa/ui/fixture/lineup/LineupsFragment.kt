package com.aroniez.futaa.ui.fixture.lineup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aroniez.futaa.R
import com.aroniez.futaa.ui.fixture.MatchDetailActivity
import com.aroniez.futaa.utils.loadMediumBannerAds
import com.aroniez.futaa.utils.showMessageLayout
import kotlinx.android.synthetic.main.fragment_lineups.*
import kotlinx.android.synthetic.main.include_ads_layout.*

class LineupsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lineups, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()

        loadMediumBannerAds(context!!, advertLayout)

    }


    private fun loadData() {
        val match = (context as MatchDetailActivity).getMatchObject()
        if (match != null) {
            if (match.lineup != null) {
                if (match.lineup!!.data.size > 0) {
                    homeTeamName.text = match.localTeam.data.name
                    awayTeamName.text = match.visitorTeam.data.name

                    val localTeamLineup = match.lineup.data.filter { it.team_id == match.localteam_id }
                    val visitingTeamLineup = match.lineup.data.filter { it.team_id == match.visitorteam_id }

                    val homeAdapter = LineupAdapter(localTeamLineup, context!!)
                    homeLineupRecycler.layoutManager = LinearLayoutManager(context)
                    homeLineupRecycler.adapter = homeAdapter
                    homeLineupRecycler.isNestedScrollingEnabled = true

                    val awayAdapter = LineupAdapter(visitingTeamLineup, context!!)
                    awayLineupRecycler.layoutManager = LinearLayoutManager(context)
                    awayLineupRecycler.adapter = awayAdapter
                    homeLineupRecycler.isNestedScrollingEnabled = true
                } else {
                    showMessageLayout("No match lineups at the moment", standingsLayout)
                }
            } else {
                showMessageLayout("No match lineups at the moment", standingsLayout)
            }

        } else {
            showMessageLayout("Error while fetching match details", standingsLayout)
        }
    }

}