package com.aroniez.futaa.ui.livegames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aroniez.futaa.AppExecutors
import com.aroniez.futaa.R
import com.aroniez.futaa.database.SoccerDatabase
import com.aroniez.futaa.utils.displayFixtures
import com.aroniez.futaa.utils.loadMediumBannerAds
import com.aroniez.futaa.utils.showMessageLayout
import kotlinx.android.synthetic.main.include_ads_layout.*
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*
import java.util.*
import kotlin.collections.ArrayList


class LiveMatchesFragment : Fragment(R.layout.include_recyclerview_progressbar_layout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                loadLiveMatchesFromDb()
            }
        }, 0, 1000 * 30)

        loadMediumBannerAds(requireContext(), advertLayout)
    }

    private fun loadLiveMatchesFromDb() {
        AppExecutors().diskIO().execute {
            if (context != null) {
                val dbInstance = SoccerDatabase.getInstance(requireContext())
                val liveMatches = dbInstance.fixtureDao().getLiveMatches()
                AppExecutors().mainThread().execute {
                    val onlyLiveMatches =
                        liveMatches.filter { it.time.status == "LIVE" || it.time.status == "HT" }
                            .sortedBy { it.time.starting_at.timestamp }
                    if (onlyLiveMatches.isNotEmpty()) {
                        displayFixtures(ArrayList(onlyLiveMatches), baseRecyclerView, requireContext(), false)
                    } else {
                        showMessageLayout("No live matches at the moment", baseNestedLayout)
                    }
                }
            }
        }
    }

}