package com.aroniez.futaa.ui.fixture

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.aroniez.futaa.AppExecutors
import com.aroniez.futaa.R
import com.aroniez.futaa.database.SoccerDatabase
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.models.leagues.LeagueRoundHeader
import com.aroniez.futaa.utils.loadRandomBannerAd
import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import kotlinx.android.synthetic.main.fixture_lite_row_item.view.*
import kotlinx.android.synthetic.main.league_round_row_item.view.*


class LeagueMatchesAdapter(val matchAdapterBundle: LeagueMatchesBundle)
    : ExpandableRecyclerAdapter<LeagueRoundHeader, Fixture, LeagueMatchesAdapter.LeagueViewHolder, LeagueMatchesAdapter.FixtureViewHolder>(matchAdapterBundle.leagues) {

    override fun onCreateParentViewHolder(parentViewGroup: ViewGroup, viewType: Int): LeagueViewHolder {
        return LeagueViewHolder(LayoutInflater.from(parentViewGroup.context!!).inflate(R.layout.league_round_row_item, parentViewGroup, false))
    }

    override fun onBindChildViewHolder(childViewHolder: FixtureViewHolder, parentPosition: Int, childPosition: Int, child: Fixture) {
        childViewHolder.bindData(child)
    }

    override fun onBindParentViewHolder(parentViewHolder: LeagueViewHolder, parentPosition: Int, parent: LeagueRoundHeader) {
        parentViewHolder.bindData(parent, parentPosition)
    }

    override fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): FixtureViewHolder {
        return FixtureViewHolder(LayoutInflater.from(childViewGroup.context!!).inflate(R.layout.fixture_lite_row_item, childViewGroup, false))
    }

    init {
        AppExecutors().diskIO().execute {
            val favoritesDao = SoccerDatabase.getInstance(matchAdapterBundle.context).favoritesDao()
            val favoriteMatchesIds = favoritesDao.getFavoriteMatchesIds()
            matchAdapterBundle.favoriteMatchesIds = favoriteMatchesIds
        }
    }

    inner class FixtureViewHolder(itemView: View) : ChildViewHolder<Fixture>(itemView) {
        fun bindData(fixture: Fixture) {
            val context = matchAdapterBundle.context
            itemView.fixtureLayout.setOnClickListener {
                val intent = Intent(context, MatchDetailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("match", fixture)
                context.startActivity(intent)
            }


            itemView.teamAName.text = fixture.localTeam.data.name
            itemView.teamBName.text = fixture.visitorTeam.data.name
            val boldTypeface = ResourcesCompat.getFont(context, R.font.font_bold)
            val regularTypeface = ResourcesCompat.getFont(context, R.font.font_medium)
            when {
                fixture.scores.localteam_score == fixture.scores.visitorteam_score -> {
                    itemView.status.background = ContextCompat.getDrawable(context, R.drawable.match_team_draw_bg)
                    itemView.status.text = "D"
                }
                fixture.scores.localteam_score < fixture.scores.visitorteam_score -> {
                    itemView.teamBName.typeface = boldTypeface
                    itemView.teamBScore.typeface = boldTypeface
                    itemView.teamAScore.typeface = regularTypeface
                    itemView.teamAName.typeface = regularTypeface
                }
                else -> {
                    itemView.teamAScore.typeface = boldTypeface
                    itemView.teamAName.typeface = boldTypeface
                    itemView.teamBScore.typeface = regularTypeface
                    itemView.teamBName.typeface = regularTypeface
                }
            }
            if (fixture.time.status == "NS") {
                itemView.teamBScore.text = "-"
                itemView.teamAScore.text = "-"
            } else {
                itemView.teamBScore.text = fixture.scores.visitorteam_score.toString()
                itemView.teamAScore.text = fixture.scores.localteam_score.toString()
            }
            itemView.date.text = fixture.time.starting_at.date.drop(5)

        }
    }

    inner class LeagueViewHolder(itemView: View) : ParentViewHolder<LeagueRoundHeader, Fixture>(itemView) {
        fun bindData(league: LeagueRoundHeader, position: Int) {
            itemView.competitionTitle.text = "Round " + league.round
            if (position != 0 && parent.fixtures!!.size > 2) {
                loadRandomBannerAd(matchAdapterBundle.context, itemView.bannerAdsLayout)
            }
        }
    }

}