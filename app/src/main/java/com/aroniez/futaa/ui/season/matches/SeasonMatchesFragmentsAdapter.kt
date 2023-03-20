package com.aroniez.futaa.ui.season.matches

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class SeasonMatchesFragmentsAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SeasonResultMatchesFragment()
            else -> SeasonUpcomingMatchesFragment()
        }
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Results"
            else -> "Upcoming"
        }
    }

}