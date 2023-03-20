package com.aroniez.futaa.models.fixture.round

import java.io.Serializable

data class Round(
        val id: Long,
        val name: Int,
        val league_id: Long,
        val season_id: Long,
        val stage_id: Long,
        val start: String,
        val end: String
) : Serializable