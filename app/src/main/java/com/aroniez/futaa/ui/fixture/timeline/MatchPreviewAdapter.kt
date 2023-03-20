package com.aroniez.futaa.ui.fixture.timeline

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aroniez.futaa.R
import com.aroniez.futaa.models.MatchPreviewItem
import kotlinx.android.synthetic.main.match_preview_away_event_item.view.*
import kotlinx.android.synthetic.main.match_preview_home_event_item.view.*


class MatchPreviewAdapter(private val events: List<MatchPreviewItem>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val advertViewType = 1
    private val homeEventViewType = 2
    private val awayEventViewType = 3

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return when (position) {
            homeEventViewType -> HomeEventViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.match_preview_home_event_item, parent, false))
            advertViewType -> SmallAdvertViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.include_ads_layout, parent, false))
            else -> AwayEventViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.match_preview_away_event_item, parent, false))
        }
    }

    override fun getItemCount() = events.size

    override fun getItemViewType(position: Int): Int {
        val eventItem = events[position]
        return if (position == 50) {
            advertViewType
        } else {
            when {
                eventItem.match_team == "home" -> homeEventViewType
                eventItem.match_team == "away" -> awayEventViewType
                else -> advertViewType
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val eventItem = events[position]
           if (holder is HomeEventViewHolder) {
            val homeViewHolder = holder
            homeViewHolder.bindData(position)
        } else if (holder is AwayEventViewHolder) {
            val awayViewHolder = holder
            awayViewHolder.bindData(position)
        } else {
            val gameViewHolder = holder as SmallAdvertViewHolder
            gameViewHolder.bindData()
        }
    }

    inner class HomeEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(position: Int) {
            val event = events[position]
            when {
                event.type == "goal" -> {
                    itemView.eventIcon.setImageResource(R.drawable.ico_football_regular_goal)
                    itemView.playerName.text = event.player_name
                }
                event.type == "card" -> {
                    if (event.card == "yellowcard") {
                        itemView.eventIcon.setImageResource(R.drawable.match_yellow_card)
                    } else {
                        itemView.eventIcon.setImageResource(R.drawable.match_red_card)
                    }
                    itemView.playerName.text = event.card_player
                }
                else -> {
                    itemView.playerName.text = event.player_in + "\n" + event.player_out
                    itemView.eventIcon.setImageResource(R.drawable.ico_substitution_in)
                }
            }
            itemView.eventTime.text = event.display_time + "\""
        }
    }

    inner class AwayEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(position: Int) {
            val event = events[position]

            when {
                event.type == "goal" -> {
                    itemView.awayEventIcon.setImageResource(R.drawable.ico_football_regular_goal)
                    itemView.awayEventName.text = event.player_name
                }
                event.type == "card" -> {
                    if (event.card == "yellowcard") {
                        itemView.awayEventIcon.setImageResource(R.drawable.match_yellow_card)
                    } else {
                        itemView.awayEventIcon.setImageResource(R.drawable.match_red_card)
                    }
                    itemView.awayEventName.text = event.card_player
                }
                else -> {
                    itemView.awayEventName.text = event.player_in + "\n" + event.player_out
                    itemView.awayEventIcon.setImageResource(R.drawable.ico_substitution_in)
                }
            }
            itemView.awayEventTime.text = event.display_time + "\""
        }
    }

    inner class SmallAdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData() {}
    }
}