package com.aroniez.futaa.ui.teams

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aroniez.futaa.ui.teams.fixtures.TeamFixturesFragment
import com.aroniez.futaa.ui.teams.overview.TeamOverviewFragment
import com.aroniez.futaa.ui.teams.results.TeamResultsFragment
import com.aroniez.futaa.ui.teams.squad.TeamSquadFragment

class TeamDetailsFragmentsAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TeamOverviewFragment()
            1 -> TeamResultsFragment()
            2 -> TeamFixturesFragment()
            3 -> TeamSquadFragment()
            else -> TeamSquadFragment()
        }
    }

    override fun getCount() = 5

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Overview"
            1 -> "Results"
            2 -> "Fixtures"
            3 -> "Squad"
            else -> "Transfers"
        }
    }

}