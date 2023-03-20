package com.aroniez.futaa.utils

import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.models.fixture.round.Round
import com.aroniez.futaa.models.leagues.CustomLeague
import com.aroniez.futaa.models.leagues.LeagueRoundHeader
import com.aroniez.futaa.ui.fixture.*
import kotlinx.android.synthetic.main.include_ads_layout.view.*
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.view.*
import kotlinx.android.synthetic.main.include_load_more_layout.view.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.view.*

fun displayFixtures(fixtures: ArrayList<Fixture>, itemView: View, context: Context, shouldOpenTeamDetails: Boolean) {
    //group fixtures first by league
    val groupedId = fixtures.groupBy { it.league_id }

    val leagues: ArrayList<CustomLeague>? = arrayListOf()
    groupedId.forEach { (_, fixtures) ->
        val league = fixtures[0].league!!.data
        if (fixtures[0].round != null) {
            league.round = fixtures[0].round!!.data
        }
        league.fixtures = fixtures.sortedByDescending { it.id }
        leagues!!.add(league)
    }

    val matchAdapterBundle = MatchAdapterBundle(leagues!!, context, shouldOpenTeamDetails)
    matchAdapterBundle.recyclerView = itemView.baseRecyclerView
    val adapter = MatchesAdapter(matchAdapterBundle)
    itemView.baseRecyclerView.layoutManager = LinearLayoutManager(context)
    itemView.baseRecyclerView.adapter = adapter

    if (itemView.advertLayout != null) {
        loadMediumBannerAds(context, itemView.advertLayout)
        Log.d("BugTracer", "itemView.advertLayout!=null")
    } else {
        Log.d("BugTracer", "itemView.advertLayout==null")
    }
}

/***
 * Show matches without team logos
 * */
fun displayFixturesLite(fixtures: List<Fixture>, itemView: View, context: Context, currentTeamId: Long) {
    val recyclerView = itemView.baseRecyclerView
    if (recyclerView != null) {
        val adapter = MatchesLiteAdapter(fixtures, context, currentTeamId)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    if (itemView.advertLayout != null) {
        loadMediumBannerAds(context, itemView.advertLayout)
    }
}

/***
 * Show matches without team logos, grouped by round
 * */
fun displayFixturesGroupedLite(fixtures: List<Fixture>, itemView: View, context: Context, round: Round) {
    //group fixtures first by league
    val groupedId = fixtures.groupBy { it.round_id }

    val leagues: ArrayList<LeagueRoundHeader>? = arrayListOf()
    groupedId.forEach { (roundId, fixtures) ->
        val roundName = (roundId - round.id) + round.name
        val league = LeagueRoundHeader(roundName.toString(), fixtures)
        league.fixtures = fixtures.sortedByDescending { it.id }
        leagues!!.add(league)
    }
    val recyclerView = itemView.baseRecyclerView
    val matchAdapterBundle = LeagueMatchesBundle(leagues!!, context, false)
    recyclerView.layoutManager = LinearLayoutManager(context)
    matchAdapterBundle.recyclerView = recyclerView
    val adapter = LeagueMatchesAdapter(matchAdapterBundle)
    recyclerView.adapter = adapter

    itemView.baseNestedLayout.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
        if (scrollY == (v!!.getChildAt(0).measuredHeight - v.measuredHeight)) {
            //show load more view
            itemView.baseLoadMoreLayout.visibility = View.VISIBLE
        }
    })

    if (itemView.advertLayout != null) {
        loadMediumBannerAds(context, itemView.advertLayout)
    }

}