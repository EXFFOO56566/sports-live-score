package com.aroniez.futaa.ui.standings

import android.content.Context
import android.content.Intent
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.aroniez.futaa.R
import com.aroniez.futaa.models.competitions.Competition
import com.aroniez.futaa.models.country.Country
import com.aroniez.futaa.models.standing.Standing
import com.aroniez.futaa.models.standing.TeamStanding
import com.aroniez.futaa.ui.teams.TeamDetailActivity
import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.standing_row_item.view.*
import kotlinx.android.synthetic.main.standings_heading_row_item.view.*
import java.util.*


class StandingsAdapter(
        private val countries: ArrayList<Standing>,
        private val context: Context,
        private val teamAId: Long,
        private val teamBId: Long
) : ExpandableRecyclerAdapter<Standing, TeamStanding, StandingsAdapter.LeagueStandingHeadingHolder, StandingsAdapter.TeamStandingViewHolder>(countries) {
    override fun onBindChildViewHolder(childViewHolder: TeamStandingViewHolder, parentPosition: Int, childPosition: Int, child: TeamStanding) {
        childViewHolder.bindData(child)
    }

    override fun onBindParentViewHolder(parentViewHolder: LeagueStandingHeadingHolder, parentPosition: Int, parent: Standing) {
        parentViewHolder.bindData(parent)
    }

    override fun onCreateParentViewHolder(parentViewGroup: ViewGroup, viewType: Int): LeagueStandingHeadingHolder {
        return LeagueStandingHeadingHolder(LayoutInflater.from(parentViewGroup.context!!).inflate(R.layout.standings_heading_row_item, parentViewGroup, false))
    }

    override fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): TeamStandingViewHolder {
        return TeamStandingViewHolder(LayoutInflater.from(childViewGroup.context!!).inflate(R.layout.standing_row_item, childViewGroup, false))
    }

    inner class LeagueStandingHeadingHolder(itemView: View) : ParentViewHolder<Country, Competition>(itemView) {

        fun bindData(standing: Standing) {
            itemView.countryName.text = standing.name
            itemView.countryFlag.visibility = View.GONE
        }
    }

    inner class TeamStandingViewHolder(itemView: View) : ChildViewHolder<Competition>(itemView) {

        fun bindData(standing: TeamStanding) {
            itemView.standingLayout.setOnClickListener {
                val intent = Intent(context, TeamDetailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("team", standing.team.data)
                context.startActivity(intent)
            }
            itemView.teamName.text = standing.team_name
            itemView.matchesPlayed.text = standing.overall.games_played.toString()
            itemView.matchesWon.text = standing.overall.won.toString()
            itemView.matchesDraw.text = standing.overall.draw.toString()
            itemView.matchesLose.text = standing.overall.lost.toString()
            itemView.goalDiff.text = standing.total.goal_difference
            itemView.points.text = standing.total.points.toString()
            itemView.position.text = (standing.position).toString()

            if (standing.result == null) {
                itemView.position.background = ContextCompat.getDrawable(context, R.drawable.standings_others_position_bg)
                itemView.position.setTextColor(ContextCompat.getColor(context, R.color.black))
            } else {
                val topPositionStatus = countries[0].standings.data[0].result
                val lastPositionStatus = countries[0].standings.data[countries[0].standings.data.size - 1].result
                if (standing.result == topPositionStatus) {
                    itemView.position.background = ContextCompat.getDrawable(context, R.drawable.standings_champions_position_bg)
                    itemView.position.setTextColor(ContextCompat.getColor(context, R.color.white))
                }
                if (standing.result == lastPositionStatus) {
                    itemView.position.background = ContextCompat.getDrawable(context, R.drawable.standings_relagation_position_bg)
                    itemView.position.setTextColor(ContextCompat.getColor(context, R.color.white))
                }
                if (standing.result == "UEFA Europa League") {
                    itemView.position.background = ContextCompat.getDrawable(context, R.drawable.standings_europa_position_bg)
                    itemView.position.setTextColor(ContextCompat.getColor(context, R.color.white))
                }
            }

            if (standing.team_id == teamAId || standing.team_id == teamBId) {
                itemView.standingLayout.background = ContextCompat.getDrawable(context, R.drawable.standings_selected_team_bg)
            } else {
                itemView.standingLayout.background = ContextCompat.getDrawable(context, R.drawable.standings_unselected_team_bg)
            }

            Picasso.get().load(standing.team.data.logo_path).placeholder(R.drawable.goals).into(itemView.teamFlag)

            val recentForm = standing.recent_form.split("").filter { it.isNotEmpty() }
            val teamForm = SpannableStringBuilder()
            for ((index, form) in recentForm.withIndex()) {
                if (form.isNotEmpty()) {
                    teamForm.append(form).setSpan(getSpanColor(form), index, index + 1, 0)
                }
            }
            itemView.recentForm.text = teamForm
        }

        private fun getSpanColor(form: String): ForegroundColorSpan {
            return when {
                form.toLowerCase(Locale.getDefault()) == "w" -> ForegroundColorSpan(ContextCompat.getColor(context, R.color.standings_recent_form_win))
                form.toLowerCase(Locale.getDefault()) == "d" -> ForegroundColorSpan(ContextCompat.getColor(context, R.color.standings_recent_form_draw))
                else -> ForegroundColorSpan(ContextCompat.getColor(context, R.color.standings_recent_form_lose))
            }
        }
    }
}