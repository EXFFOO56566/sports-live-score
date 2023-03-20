package com.aroniez.futaa.models.seasons.topscorers


class TopScorer(
        val id: Long,
        val league_id: Long,
        val current_round_id: Long,
        val current_stage_id: Long,
        val is_current_season: Boolean,
        val name: String,
        val goalscorers: GoalScorerData,
        val assistscorers: AssistScorerData,
        val cardscorers: CardScorerData
)