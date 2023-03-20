package com.aroniez.futaa.ui.fixture.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aroniez.futaa.R
import com.aroniez.futaa.models.MatchPreviewItem
import com.aroniez.futaa.ui.fixture.MatchDetailActivity
import com.aroniez.futaa.utils.*
import kotlinx.android.synthetic.main.include_ads_layout.*
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*

class MatchOverviewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.include_recyclerview_progressbar_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()

        loadMediumBannerAds(context!!, advertLayout)

    }


    private fun loadData() {
        val match = (context as MatchDetailActivity).getMatchObject()

        if (match != null) {
            val matchPreviewItems: ArrayList<MatchPreviewItem> = arrayListOf()
            val goals = match.goals!!.data
            val cards = match.cards!!.data
            val substitutions = match.substitutions!!.data

            if ((substitutions.size + goals.size + cards.size) > 0) {
                for (goal in goals) {
                    val matchPreviewItem = MatchPreviewItem()
                    if (goal.team_id.toLong() == match.localteam_id) {
                        matchPreviewItem.match_team = "home"
                    } else {
                        matchPreviewItem.match_team = "away"
                    }
                    matchPreviewItem.type = "goal"
                    matchPreviewItem.time = goal.minute
                    matchPreviewItem.display_time = goal.minute.toString()
                    matchPreviewItem.player_name = goal.player_name
                    matchPreviewItem.score = goal.result
                    matchPreviewItem.player_assist_name = goal.player_assist_name
                    matchPreviewItems.add(matchPreviewItem)
                }

                for (card in cards) {
                    val matchPreviewItem = MatchPreviewItem()
                    if (card.team_id.toLong() == match.localteam_id) {
                        matchPreviewItem.match_team = "home"
                    } else {
                        matchPreviewItem.match_team = "away"
                    }
                    matchPreviewItem.type = "card"
                    matchPreviewItem.time = card.minute
                    matchPreviewItem.display_time = card.minute.toString()
                    matchPreviewItem.card = card.type
                    matchPreviewItem.card_player = card.player_name
                    matchPreviewItems.add(matchPreviewItem)
                }

                for (homeSub in substitutions) {
                    val matchPreviewItem = MatchPreviewItem()
                    if (homeSub.team_id.toLong() == match.localteam_id) {
                        matchPreviewItem.match_team = "home"
                    } else {
                        matchPreviewItem.match_team = "away"
                    }
                    matchPreviewItem.type = "substitution"
                    matchPreviewItem.time = homeSub.minute
                    matchPreviewItem.display_time = homeSub.minute.toString()
                    matchPreviewItem.player_in = homeSub.player_in_name
                    matchPreviewItem.player_out = homeSub.player_out_name
                    matchPreviewItems.add(matchPreviewItem)
                }

                matchPreviewItems.sortByDescending { it.time }

                val awayAdapter = MatchPreviewAdapter(matchPreviewItems, context!!)
                baseRecyclerView.layoutManager = LinearLayoutManager(context)
                baseRecyclerView.adapter = awayAdapter
                baseRecyclerView.isNestedScrollingEnabled = true
            } else {
                showMessageLayout("No match preview at the moment", baseNestedLayout)
            }


        } else {
            showMessageLayout("Could not fetch match preview", baseNestedLayout)
        }
    }
}