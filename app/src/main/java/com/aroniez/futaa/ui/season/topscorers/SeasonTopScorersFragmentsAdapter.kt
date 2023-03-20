package com.aroniez.futaa.ui.season.topscorers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aroniez.futaa.models.seasons.topscorers.TopScorer

class SeasonTopScorersFragmentsAdapter(fragmentManager: FragmentManager, private val topScorers: TopScorer) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SeasonGoalScorersFragment(topScorers)
            1 -> SeasonAssistScorersFragment(topScorers)
            else -> SeasonCardScorersFragment(topScorers)
        }
    }

    override fun getCount() = 3

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Goals"
            1 -> "Assists"
            else -> "Cards"
        }
    }

}