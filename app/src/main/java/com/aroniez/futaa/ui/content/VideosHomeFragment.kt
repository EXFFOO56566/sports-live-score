package com.aroniez.futaa.ui.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aroniez.futaa.R
import kotlinx.android.synthetic.main.include_viewpager_tabs_layout.*


class VideosHomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_viewpager_with_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewpager.adapter = VideoHomeFragmentsAdapter(childFragmentManager, context!!)
        tabLayout.setupWithViewPager(viewpager)
        viewpagerTabsLayout.visibility = View.VISIBLE

    }
}
