package com.aroniez.futaa.models.team.transfers

import com.aroniez.futaa.models.fixture.lineup.PlayerData
import com.aroniez.futaa.models.fixture.stats.Statistic
import com.aroniez.futaa.models.fixture.stats.StatsData
import com.aroniez.futaa.models.fixture.team.TeamData
import java.io.Serializable

class Transfer(
        val player_id: Long,
        val from_team_id: Long,
        val to_team_id: Long,
        val season_id: Long,
        val transfer: String,
        val type: String,
        val date: String,
        val amount: String? = null,
        val player: PlayerData,
        val team: TeamData
) : Serializable