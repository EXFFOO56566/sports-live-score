package com.aroniez.futaa.models.seasons

import com.aroniez.futaa.api.callbacks.MatchesCallback
import com.aroniez.futaa.models.fixture.round.RoundData


class SeasonMatches(
        val id: Long,
        val name: Boolean,
        val league_id: Long,
        val is_current_season: Boolean,
        val current_round_id: Long,
        val current_stage_id: Long,
        val results: MatchesCallback,
        val round: RoundData,
        val upcoming: MatchesCallback
)