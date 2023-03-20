package com.aroniez.futaa.database.converters.country

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.round.Round
import com.aroniez.futaa.models.leagues.League
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CountryExtraConverter {
    @TypeConverter
    fun fromString(value: String): Round? {
        val listType = object : TypeToken<Round>() {}.type
        return Gson().fromJson<Round>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: Round?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}