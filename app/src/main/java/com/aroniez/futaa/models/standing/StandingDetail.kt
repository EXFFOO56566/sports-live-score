package com.aroniez.futaa.models.standing

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

class StandingDetail(
        val games_played: Int,
        val won: Int,
        val draw: Int,
        val lost: Int,
        val goals_scored: Int,
        val goals_against: Int
) : Serializable {
}
