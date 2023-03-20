package com.aroniez.futaa.models.odds

data class Bookmaker(
        var id: Long,
        var name: String,
        var odds: OddValuesData
)