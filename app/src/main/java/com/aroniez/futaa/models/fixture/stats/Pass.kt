package com.aroniez.futaa.models.fixture.stats

import java.io.Serializable

data class Pass(
        val total: Int,
        val accurate: Int,
        val percentage: Int
) : Serializable