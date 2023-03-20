package com.aroniez.futaa.models.fixture.time

import java.io.Serializable

class StartingAt(
    val date_time: String,
    val date: String,
    val time: String,
    val timezone: String,
    val timestamp: Long
): Serializable