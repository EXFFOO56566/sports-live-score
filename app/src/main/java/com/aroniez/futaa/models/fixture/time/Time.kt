package com.aroniez.futaa.models.fixture.time

import java.io.Serializable

class Time(
        val status: String,
        val starting_at: StartingAt,
        val minute: Int,
        val second: Int? = null,
        val added_time: Int? = null,
        val extra_minute: Int? = null,
        val injury_time: Int? = null
) : Serializable