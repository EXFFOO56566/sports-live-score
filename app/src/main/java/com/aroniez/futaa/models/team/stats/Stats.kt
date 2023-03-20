package com.aroniez.futaa.models.team.stats

import java.io.Serializable

data class Stats(
        val team_id: Long,
        val season_id: Long,
        val stage_id: Long? = null,
        val win: ScoreStats,
        val draw: ScoreStats,
        val lost: ScoreStats,
        val goals_for: ScoreStats,
        val goals_against: ScoreStats,
        val clean_sheet: ScoreStats,
        val failed_to_score: ScoreStats,
        val avg_goals_per_game_scored: ScoreStats,
        val avg_goals_per_game_conceded: ScoreStats,
        val avg_first_goal_scored: ScoreStats,
        val avg_first_goal_conceded: ScoreStats,
        val scoring_minutes: ScoringMinsData,
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