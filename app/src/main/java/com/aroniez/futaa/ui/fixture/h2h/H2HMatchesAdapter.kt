package com.aroniez.futaa.ui.fixture.h2h

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aroniez.futaa.R
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.ui.fixture.MatchDetailActivity
import kotlinx.android.synthetic.main.h2h_row_item.view.*


class H2HMatchesAdapter(private val games: List<Fixture>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val typeAdvert = 1
    private val typeGame = 2
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return when (position) {
            typeGame -> LiveGameViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.h2h_row_item, parent, false))
            typeAdvert -> SmallAdvertViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.include_ads_layout, parent, false))
            else -> LiveGameViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.h2h_row_item, parent, false))
        }
    }

    override fun getItemCount() = games.size

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
            val fixture = games[position]
            itemView.fixtureLayout.setOnClickListener {
                val intent = Intent(context, MatchDetailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("match", fixture)
                context.startActivity(intent)
            }


            itemView.teamAName.text = fixture.localTeam.data.name
            itemView.teamBName.text = fixture.visitorTeam.data.name
            when {
                fixture.scores.localteam_score == fixture.scores.visitorteam_score -> {

                }
                fixture.scores.localteam_score < fixture.scores.visitorteam_score -> {
                    itemView.teamBName.setTypeface(null, Typeface.BOLD)
                    itemView.teamBScore.setTypeface(null, Typeface.BOLD)
                    itemView.teamAScore.setTypeface(null, Typeface.NORMAL)
                    itemView.teamAName.setTypeface(null, Typeface.NORMAL)
                }
                else -> {
                    itemView.teamAScore.setTypeface(null, Typeface.BOLD)
                    itemView.teamAName.setTypeface(null, Typeface.BOLD)
                    itemView.teamBScore.setTypeface(null, Typeface.NORMAL)
                    itemView.teamBName.setTypeface(null, Typeface.NORMAL)
                }
            }
            itemView.teamBScore.text = fixture.scores.visitorteam_score.toString()
            itemView.teamAScore.text = fixture.scores.localteam_score.toString()
        }
    }

    inner class SmallAdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData() {
        }
    }
}