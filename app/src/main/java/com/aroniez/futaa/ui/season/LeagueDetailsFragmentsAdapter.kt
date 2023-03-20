package com.aroniez.futaa.ui.season

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.aroniez.futaa.ui.season.matches.SeasonFixturesFragment
import com.aroniez.futaa.ui.season.topscorers.SeasonTopScorersFragment
import com.aroniez.futaa.ui.standings.LeagueStandingsFragment

class LeagueDetailsFragmentsAdapter(
        fragmentManager: FragmentManager,
        private val hasStandings: Boolean,
        private val seasonId: Long
) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return if (hasStandings) {
            when (position) {
                0 -> {
                    val bundle = Bundle()
                    bundle.putLong("season_id", seasonId)
                    bundle.putLong("home_id", 0L)
                    bundle.putLong("away_id", 0L)
                    val fragment = LeagueStandingsFragment()
                    fragment.arguments = bundle
                    fragment
                }
                1 -> SeasonFixturesFragment()
                else -> SeasonTopScorersFragment()
            }
        } else {
            when (position) {
                0 -> SeasonFixturesFragment()
                else -> SeasonTopScorersFragment()
            }
        }
    }

    override fun getCount(): Int {
        return if (hasStandings) {
            3
        } else {
            2
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (hasStandings) {
            when (position) {
                0 -> "Standings"
                1 -> "Matches"
                else -> "Top Scorers"
            }
        } else {
            when (position) {
                0 -> "Matches"
                else -> "Top Scorers"
            }
        }

    }

}