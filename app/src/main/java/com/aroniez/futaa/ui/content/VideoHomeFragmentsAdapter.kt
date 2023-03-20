package com.aroniez.futaa.ui.content

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aroniez.futaa.ui.content.news.NewsFragment
import com.aroniez.futaa.ui.content.videos.VideosFragment

class VideoHomeFragmentsAdapter(fragmentManager: FragmentManager, val context: Context) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> VideosFragment()
            else -> NewsFragment()
        }
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Video Highlights"
            else -> "Soccer News"
        }
    }

}