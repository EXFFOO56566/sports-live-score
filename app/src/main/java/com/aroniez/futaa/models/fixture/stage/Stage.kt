package com.aroniez.futaa.models.fixture.stage

import java.io.Serializable

data class Stage(
        val id: Long,
        val name: String,
        val type: String,
        val league_id: Long,
        val season_id: Long,
        val sort_order: Int,
        val has_standings: Boolean
) : Serializable