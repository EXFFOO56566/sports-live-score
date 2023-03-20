package com.aroniez.futaa.ui.teams.squad

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aroniez.futaa.R
import com.aroniez.futaa.models.seasons.players.PlayerRanking
import com.aroniez.futaa.utils.AdmobAdsUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.include_ads_layout.view.*
import kotlinx.android.synthetic.main.squad_row_item.view.*


class TeamSquadAdapter(private val teams: ArrayList<PlayerRanking>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val typeAdvert = 1
    private val typeGame = 2
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return when (position) {
            typeGame -> LiveGameViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.squad_row_item, parent, false))
            typeAdvert -> SmallAdvertViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.include_ads_layout, parent, false))
            else -> LiveGameViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.squad_row_item, parent, false))
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

            Picasso.get().load(playerRanking.player.data.image_path).into(itemView.playerPicture)
            itemView.teamName.text = playerRanking.player.data.common_name
            itemView.playerCountry.text = playerRanking.player.data.birthcountry
            if (playerRanking.position != null) {
                itemView.positionName.text = playerRanking.position.data.name
            }

            itemView.playerLayout.setOnClickListener {

            }

        }
    }

    inner class SmallAdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData() {
            AdmobAdsUtil.loadBannerAds(context, itemView.advertLayout)
        }
    }
}