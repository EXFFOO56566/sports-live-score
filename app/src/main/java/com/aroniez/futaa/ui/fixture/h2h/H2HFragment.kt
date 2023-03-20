package com.aroniez.futaa.ui.fixture.h2h

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aroniez.futaa.R
import com.aroniez.futaa.ui.fixture.MatchDetailActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.include_viewpager_tabs_layout.*


class H2HFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_viewpager_with_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val match = (context as MatchDetailActivity).getMatchObject()
        if (viewpager != null) {
            viewpager.adapter = H2HFragmentsAdapter(childFragmentManager, match!!)
            tabLayout.setupWithViewPager(viewpager)
            viewpager.offscreenPageLimit = 2
            viewpagerTabsLayout.visibility = View.VISIBLE
            val scale = context!!.resources.displayMetrics.density
            tabLayout.layoutParams.height = (24 * scale + 0.5F).toInt()
            tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
            tabLayout.requestLayout()
            tabLayout.background = ContextCompat.getDrawable(context!!, R.drawable.standings_unselected_team_bg)
        }
    }
}
