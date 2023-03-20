package com.aroniez.futaa.models.fixture

import java.io.Serializable

data class LineupEnvelop(
    val home: Lineup,
    val away: Lineup
):Serializable