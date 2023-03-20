package com.aroniez.futaa.models.odds

data class OddValues(
        var label: String,
        var value: String,
        var extra: String,
        var probability: String,
        var dp3: String,
        var american: Int,
        var total: String,
        var winning: Boolean,
        var stop: Boolean,
        var bookmaker_event_id: Long
)