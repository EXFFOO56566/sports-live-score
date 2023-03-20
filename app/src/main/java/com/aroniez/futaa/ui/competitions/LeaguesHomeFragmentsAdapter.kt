package com.aroniez.futaa.ui.competitions

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aroniez.futaa.R
import com.aroniez.futaa.ui.competitions.countries.CountriesFragment
import com.aroniez.futaa.ui.competitions.leagues.Competitions1Fragment

class LeaguesHomeFragmentsAdapter(fragmentManager: FragmentManager, val context: Context) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Competitions1Fragment()
            else -> CountriesFragment()
        }
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.tab_popular_title)
            else -> context.getString(R.string.tab_countries_title)
        }
    }

}