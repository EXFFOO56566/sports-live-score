package com.aroniez.futaa.models.seasons.topscorers

import com.aroniez.futaa.models.fixture.lineup.PlayerData
import com.aroniez.futaa.models.fixture.team.TeamData


class CardScorer(
        val position: Int,
        val season_id: Long,
        val player_id: Long,
        val team_id: Long,
        val stage_id: Long,
        val team: TeamData,
        val yellowcards: Int,
        val redcards: Int,
        val type: String,
        val player: PlayerData
)