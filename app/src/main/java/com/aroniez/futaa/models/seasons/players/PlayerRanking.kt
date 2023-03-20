package com.aroniez.futaa.models.seasons.players


class PlayerRanking(
        val player_id: Long,
        val position_id: Long,
        val number: Int,
        val captain: Int,
        val minutes: Int,
        val appearences: Int,
        val lineups: Int,
        val substitute_in: Int,
        val substitute_out: Int,
        val goals: Int,
        val substitutes_on_bench: Int,
        val assists: Int,
        val saves: Int? = null,
        val inside_box_saves: Int? = null,
        val dispossesed: Int? = null,
        val interceptions: Int? = null,
        val tackles: Int? = null,
        val blocks: Int? = null,
        val hit_post: Int? = null,
        val fouls: Foul,
        val crosses: Cross,
        val dribbles: Dribble,
        val duels: Duel,
        val passes: Pass,
        val penalties: Penalty,
        val shots: Shot,
        val player: PlayerRankingData,
        val position: PositionData
)