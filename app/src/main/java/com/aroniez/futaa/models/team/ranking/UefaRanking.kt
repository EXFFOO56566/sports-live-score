package com.aroniez.futaa.models.team.ranking


class UefaRanking(
        val team_id: Long,
        val points: Float,
        val coeffiecient: Double,
        val position: Int,
        val position_won_or_lost: Int,
        val position_status: String
)