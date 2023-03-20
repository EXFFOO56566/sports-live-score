package com.aroniez.futaa.database.converters.league

import androidx.room.TypeConverter
import com.aroniez.futaa.models.competitions.CompetitionData
import com.aroniez.futaa.models.country.CompetitionsData
import com.aroniez.futaa.models.fixture.round.RoundData
import com.aroniez.futaa.models.leagues.CustomLeague
import com.aroniez.futaa.models.leagues.LeagueData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CustomLeagueConverter {
    @TypeConverter
    fun fromString(value: String): CustomLeague? {
        val listType = object : TypeToken<CustomLeague>() {}.type
        return Gson().fromJson<CustomLeague>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: CustomLeague?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}