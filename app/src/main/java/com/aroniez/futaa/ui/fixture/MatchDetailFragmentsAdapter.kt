package com.aroniez.futaa.ui.fixture

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aroniez.futaa.ui.fixture.chats.MatchChatsFragment
import com.aroniez.futaa.ui.fixture.commentary.CommentariesFragment
import com.aroniez.futaa.ui.fixture.h2h.H2HFragment
import com.aroniez.futaa.ui.fixture.lineup.LineupsFragment
import com.aroniez.futaa.ui.fixture.predictions.MatchPredictionsFragment
import com.aroniez.futaa.ui.fixture.timeline.MatchOverviewFragment
import com.aroniez.futaa.ui.standings.LeagueStandingsFragment

class MatchDetailFragmentsAdapter(
        fragmentManager: FragmentManager,
        private val seasonId: Long,
        private val homeTeamId: Long,
        private val awayTeamId: Long
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MatchPredictionsFragment()
            1 -> {
                MatchChatsFragment()
            }
            2 -> {
                val bundle = Bundle()
                bundle.putLong("season_id", seasonId)
                bundle.putLong("home_id", homeTeamId)
                bundle.putLong("away_id", awayTeamId)
                val fragment = LeagueStandingsFragment()
                fragment.arguments = bundle
                fragment
            }
            3 -> CommentariesFragment()
            4 -> H2HFragment()
            5 -> LineupsFragment()
            else -> MatchOverviewFragment()
        }
    }

    override fun getCount() = 7

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Predictions"
            1 -> "Chats"
            2 -> "Standings"
            3 -> "Commentary"
            4 -> "Matches"
            5 -> "Lineup"
            else -> "Timeline"
        }
    }

}