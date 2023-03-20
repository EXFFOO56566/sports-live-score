package com.aroniez.futaa.ui.fixture.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aroniez.futaa.ui.matches.BaseMatchesFragment
import com.aroniez.futaa.utils.DateTimeUtil

class MatchHomeFragmentsAdapter(fragmentManager: FragmentManager, val context: Context) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val bundle = Bundle()
                bundle.putString("from", DateTimeUtil.getCustomDate(-14))
                bundle.putString("to", DateTimeUtil.getCustomDate(-1))
                val fragment = BaseMatchesFragment()
                fragment.arguments = bundle
                fragment
            }
            else -> {
                val bundle = Bundle()
                bundle.putString("from", DateTimeUtil.getCustomDate(0))
                bundle.putString("to", DateTimeUtil.getCustomDate(14))
                val fragment = BaseMatchesFragment()
                fragment.arguments = bundle
                fragment
            }
        }
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Recent"
            else -> "Upcoming"
        }
    }

}