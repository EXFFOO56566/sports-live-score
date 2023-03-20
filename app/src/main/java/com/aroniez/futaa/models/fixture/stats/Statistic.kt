package com.aroniez.futaa.models.fixture.stats

import java.io.Serializable

data class Statistic(
        val team_id: Long,
        val fixture_id: Long,
        val shots: Shot,
        val passes: Pass,
        val attacks: Attack,
        val fouls: Int,
        val corners: Int,
        val offsides: Int,
        val possessiontime: Int,
        val yellowcards: Int,
        val redcards: Int,
        val yellowredcards: Int,
        val saves: Int,
        val substitutions: Int,
        val goal_kick: Int,
        val goal_attempts: Int,
        val free_kick: Int,
        val throw_in: Int,
        val ball_safe: Int,
        val goals: Int,
        val penalties: Int,
        val injuries: Int? = null
) : Serializable