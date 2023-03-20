package com.aroniez.futaa.ui.topscorers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aroniez.futaa.R
import com.aroniez.futaa.models.seasons.topscorers.GoalScorer
import com.aroniez.futaa.utils.AdmobAdsUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.include_ads_layout.view.*
import kotlinx.android.synthetic.main.top_scorer_row_item.view.*


class TopGoalScorerAdapter(private val teams: ArrayList<GoalScorer>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val typeAdvert = 1
    private val typeGame = 2
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return when (position) {
            typeGame -> GoalScorerViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.top_scorer_row_item, parent, false))
            typeAdvert -> SmallAdvertViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.include_ads_layout, parent, false))
            else -> GoalScorerViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.top_scorer_row_item, parent, false))
        }
    }

    override fun getItemCount() = teams.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 50) {
            typeAdvert
        } else {
            typeGame
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GoalScorerViewHolder) {
            holder.bindData(position)
        } else {
            val gameViewHolder = holder as SmallAdvertViewHolder
            gameViewHolder.bindData()
            gameViewHolder.setIsRecyclable(false)
        }
    }

    inner class GoalScorerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(position: Int) {
            val playerRanking = teams[position]

            Picasso.get().load(playerRanking.player.data.image_path).into(itemView.playerPicture)
            Picasso.get().load(playerRanking.team.data.logo_path).into(itemView.teamFlag)
            itemView.teamName.text = playerRanking.player.data.common_name
            itemView.countryName.text = playerRanking.player.data.nationality
            itemView.totals.text = playerRanking.goals.toString()
            itemView.position.text = (position + 1).toString()

        }
    }

    inner class SmallAdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData() {
            AdmobAdsUtil.loadBannerAds(context, itemView.advertLayout)
        }
    }
}