package com.aroniez.futaa.database.converters.league

import androidx.room.TypeConverter
import com.aroniez.futaa.models.competitions.CompetitionData
import com.aroniez.futaa.models.country.CompetitionsData
import com.aroniez.futaa.models.fixture.round.RoundData
import com.aroniez.futaa.models.leagues.LeagueData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class LeagueDataConverter {
    @TypeConverter
    fun fromString(value: String): CompetitionData? {
        val listType = object : TypeToken<CompetitionData>() {}.type
        return Gson().fromJson<CompetitionData>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: CompetitionData?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}