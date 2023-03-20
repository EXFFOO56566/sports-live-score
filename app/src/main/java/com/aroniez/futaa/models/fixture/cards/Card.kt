package com.aroniez.futaa.models.fixture.cards

import java.io.Serializable


class Card(
        val id: Long,
        val team_id: String,
        val type: String,
        val fixture_id: Long,
        val player_id: Long,
        val player_name: String,
        val minute: Int,
        val extra_minute: Int? = null,
        val reason: Int? = null
): Serializable