package com.aroniez.futaa.models.standing

import androidx.room.PrimaryKey
import com.bignerdranch.expandablerecyclerview.model.Parent
import java.io.Serializable

class Standing(
        @PrimaryKey
        val id: Long,
        val name: String,
        val league_id: Long,
        val season_id: Long,
        val round_id: Long,
        val round_name: Long,
        val stage_id: Long,
        val type: String,
        val stage_name: String,
        val resource: String,
        val standings: StandingData
) : Serializable, Parent<TeamStanding> {
    override fun getChildList() = standings.data

    override fun isInitiallyExpanded() = true
}
