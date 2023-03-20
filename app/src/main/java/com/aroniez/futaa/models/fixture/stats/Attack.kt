package com.aroniez.futaa.models.fixture.stats

import java.io.Serializable

data class Attack(
        val attacks: Int,
        val dangerous_attacks: Int
) : Serializable