package com.aroniez.futaa.ui.season.topscorers

import androidx.recyclerview.widget.LinearLayoutManager
import com.aroniez.futaa.models.seasons.topscorers.TopScorer
import com.aroniez.futaa.ui.topscorers.TopGoalScorerAdapter
import com.aroniez.futaa.utils.showMessageLayout
import kotlinx.android.synthetic.main.fragment_recyclerview_no_loading.*
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*


class SeasonGoalScorersFragment(private val topScorers: TopScorer) : SeasonTopScorersBaseFragment() {
    override fun displayTopScorers() {
        if (topScorers.goalscorers.data.size > 0) {
            val adapter = TopGoalScorerAdapter(topScorers.goalscorers.data, context!!)
            baseRecyclerView.layoutManager = LinearLayoutManager(context)
            baseRecyclerView.adapter = adapter
        } else {
            showMessageLayout("No goal scorers at the moment", baseNestedLayout)
        }
    }
}