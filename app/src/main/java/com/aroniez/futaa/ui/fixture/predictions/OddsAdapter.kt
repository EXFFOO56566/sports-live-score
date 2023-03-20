package com.aroniez.futaa.ui.fixture.predictions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aroniez.futaa.R
import com.aroniez.futaa.models.odds.Odd
import kotlinx.android.synthetic.main.odds_row_item.view.*


class OddsAdapter(private val games: List<Odd>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val typeAdvert = 1
    private val typeGame = 2
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return when (position) {
            typeGame -> LiveGameViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.odds_row_item, parent, false))
            else -> LiveGameViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.include_ads_layout, parent, false))
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
            val oddValues = games[position]
            val bookmaker = oddValues.bookmaker.data[0]
            itemView.bookmakerName.text = bookmaker.name
            itemView.oddType.text = oddValues.name
            itemView.teamAOdds.text = bookmaker.odds.data[0].value
            if (oddValues.id == 976105L) {
                itemView.drawOdds.visibility = View.GONE
                itemView.teamBOdds.text = bookmaker.odds.data[1].value
            } else {
                itemView.drawOdds.visibility = View.VISIBLE
                itemView.teamBOdds.text = bookmaker.odds.data[2].value
                itemView.drawOdds.text = bookmaker.odds.data[1].value
            }
        }
    }

    inner class SmallAdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData() {
        }
    }
}