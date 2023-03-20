package com.aroniez.futaa.models.fixture.lineup

import java.io.Serializable


class Player(
        val team_id: Long,
        val fixture_id: Long,
        val player_id: Long,
        val number: Int,
        val player_name: String,
        val position: String,
        val additional_position: String? = null,
        val posx: String? = null,
        val posy: String? = null,
        val formation_position: Int,
        val captain: Boolean
): Serializable