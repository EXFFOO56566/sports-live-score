package com.aroniez.futaa.models.standing

import com.aroniez.futaa.models.fixture.team.TeamData
import java.io.Serializable

class TeamStanding(
        val position: Int,
        val team_id: Long,
        val team_name: String,
        val round_id: Long,
        val round_name: Int,
        val group_id: Long? = null,
        val group_name: String? = null,
        val overall: StandingDetail,
        val home: StandingDetail,
        val away: StandingDetail,
        val StandingTotal: StandingDetail,
        val points: Int,
        val total: StandingTotal,
        val result: String,
        val recent_form: String,
        val team: TeamData,
        val status: String? = null
) : Serializable
