package com.aroniez.futaa.ui.competitions.leagues

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aroniez.futaa.R
import com.aroniez.futaa.models.competitions.Competition
import com.aroniez.futaa.ui.season.LeagueDetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.league_title_row.view.*

class Competitions1Adapter(private val leagues: List<Competition>, private val context: Context) : RecyclerView.Adapter<Competitions1Adapter.LiveGameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): LiveGameViewHolder {
        return LiveGameViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.league_title_row, parent, false))
    }

    override fun getItemCount() = leagues.size

    override fun onBindViewHolder(holder: LiveGameViewHolder, position: Int) = holder.bindData(position)

    inner class LiveGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(position: Int) {
            val league = leagues[position]

            itemView.competitionTitle.text = league.country!!.data.name + ": " + league.name
            Picasso.get().load(league.logo_path).placeholder(R.drawable.goals).into(itemView.leagueFlag)

            if (league.season != null) {
                itemView.competitionRound.visibility = View.VISIBLE
                if (league.season!!.data.round != null) {
                    itemView.competitionRound.text = "Round " + league.season!!.data.round!!.data.name
                } else if (league.season!!.data.stage != null) {
                    itemView.competitionRound.text = league.season!!.data.stage!!.data.name
                } else {
                    itemView.competitionRound.visibility = View.GONE
                }
            } else {
                itemView.competitionRound.visibility = View.GONE
            }

            itemView.leagueLayout.setOnClickListener {
                val intent = Intent(context, LeagueDetailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("season_id", league.current_season_id)
                intent.putExtra("live_standings", league.live_standings)
                intent.putExtra("logo_path", league.logo_path)
                intent.putExtra("country_name", league.country.data.name)
                intent.putExtra("league_name", league.name)
                intent.putExtra("coverage", league.coverage)
                context.startActivity(intent)
            }
        }
    }
}