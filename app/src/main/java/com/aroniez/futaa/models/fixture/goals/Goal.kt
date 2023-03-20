package com.aroniez.futaa.models.fixture.goals

import java.io.Serializable


class Goal(
        val id: Long,
        val team_id: String,
        val type: String,
        val fixture_id: Long,
        val player_id: Long,
        val player_assist_id: Long,
        val player_name: String,
        val player_assist_name: String,
        val result: String,
        val minute: Int,
        val extra_minute: Int? = null,
        val reason: Int? = null
): Serializable