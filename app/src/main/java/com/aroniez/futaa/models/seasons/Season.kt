package com.aroniez.futaa.models.seasons

import com.aroniez.futaa.models.fixture.round.RoundData
import com.aroniez.futaa.models.fixture.stage.StageData


class Season(
        val id: Long,
        val name: Boolean,
        val league_id: Long,
        val is_current_season: Boolean,
        val current_round_id: Long,
        val stage: StageData?,
        val round: RoundData?,
        val current_stage_id: Long
)