package com.aroniez.futaa.ui.fixture

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.aroniez.futaa.R
import com.aroniez.futaa.models.fixture.Fixture
import kotlinx.android.synthetic.main.fixture_lite_row_item.view.*


class MatchesLiteAdapter(
        private val fixtures: List<Fixture>,
        private val context: Context,
        private val currentTeamId: Long
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val typeAdvert = 1
    private val typeGame = 2
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return when (position) {
            typeGame -> LiveGameViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.fixture_lite_row_item, parent, false))
            typeAdvert -> SmallAdvertViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.include_ads_layout, parent, false))
            else -> LiveGameViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.fixture_lite_row_item, parent, false))
        }
    }

    override fun getItemCount() = fixtures.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 50) {
            typeAdvert
        } else {
            typeGame
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LiveGameViewHolder) {
            holder.bindData(position)
        } else {
            val gameViewHolder = holder as SmallAdvertViewHolder
            gameViewHolder.bindData()
        }
    }

    inner class LiveGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(position: Int) {
            val fixture = fixtures[position]

            itemView.fixtureLayout.setOnClickListener {
                val intent = Intent(context, MatchDetailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("match", fixture)
                context.startActivity(intent)
            }

            if (currentTeamId == 0L) {
                itemView.status.visibility = View.GONE
            } else {
                itemView.status.visibility = View.VISIBLE
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
                    if (currentTeamId == fixture.localteam_id) {
                        itemView.status.background = ContextCompat.getDrawable(context, R.drawable.match_team_lose_bg)
                        itemView.status.text = "L"
                    } else {
                        itemView.status.background = ContextCompat.getDrawable(context, R.drawable.match_team_win_bg)
                        itemView.status.text = "W"
                    }
                }
                else -> {
                    itemView.teamAScore.typeface = boldTypeface
                    itemView.teamAName.typeface = boldTypeface
                    itemView.teamBScore.typeface = regularTypeface
                    itemView.teamBName.typeface = regularTypeface

                    if (currentTeamId == fixture.visitorteam_id) {
                        itemView.status.background = ContextCompat.getDrawable(context, R.drawable.match_team_lose_bg)
                        itemView.status.text = "L"
                    } else {
                        itemView.status.text = "W"
                        itemView.status.background = ContextCompat.getDrawable(context, R.drawable.match_team_win_bg)
                    }
                }
            }
            if (fixture.time.status == "NS") {
                itemView.status.visibility = View.GONE
                itemView.teamBScore.text = "-"
                itemView.teamAScore.text = "-"
            } else {
                itemView.teamBScore.text = fixture.scores.visitorteam_score.toString()
                itemView.status.visibility = View.VISIBLE
                itemView.teamAScore.text = fixture.scores.localteam_score.toString()
            }
            itemView.date.text = fixture.time.starting_at.date.drop(5)
        }
    }

    inner class SmallAdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData() {
        }
    }
}