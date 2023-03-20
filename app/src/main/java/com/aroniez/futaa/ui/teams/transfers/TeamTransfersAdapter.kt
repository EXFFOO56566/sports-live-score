package com.aroniez.futaa.ui.teams.transfers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aroniez.futaa.R
import com.aroniez.futaa.models.team.transfers.Transfer
import com.aroniez.futaa.ui.fixture.MatchDetailActivity
import com.aroniez.futaa.utils.AdmobAdsUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.include_ads_layout.view.*
import kotlinx.android.synthetic.main.team_row_item.view.*


class TeamTransfersAdapter(private val teams: ArrayList<Transfer>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val typeAdvert = 1
    private val typeGame = 2
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return when (position) {
            typeGame -> LiveGameViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.top_scorer_row_item, parent, false))
            typeAdvert -> SmallAdvertViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.include_ads_layout, parent, false))
            else -> LiveGameViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.top_scorer_row_item, parent, false))
        }
    }

    override fun getItemCount() = teams.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 5) {
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
            gameViewHolder.setIsRecyclable(false)
        }
    }

    inner class LiveGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(position: Int) {
            val playerRanking = teams[position]

            if (playerRanking.player != null) {
                Picasso.get().load(playerRanking.player.data.image_path).into(itemView.teamFlag)
                itemView.teamName.text = playerRanking.player.data.common_name
                itemView.position.text = (position + 1).toString()
                itemView.teamLayout.setOnClickListener {
                    val intent = Intent(context, MatchDetailActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    intent.putExtra("player", playerRanking.player.data)
                    context.startActivity(intent)
                }
            }

        }
    }

    inner class SmallAdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData() {
            AdmobAdsUtil.loadBannerAds(context, itemView.advertLayout)
        }
    }
}