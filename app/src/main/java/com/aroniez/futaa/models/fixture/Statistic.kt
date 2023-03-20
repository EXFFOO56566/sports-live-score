package com.aroniez.futaa.models.fixture

import java.io.Serializable

data class Statistic(
    val type: String,
    val home: String,
    val away: String
): Serializable