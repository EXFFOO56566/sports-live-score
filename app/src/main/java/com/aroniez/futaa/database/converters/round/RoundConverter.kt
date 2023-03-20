package com.aroniez.futaa.database.converters.round

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.round.Round
import com.aroniez.futaa.models.leagues.League
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class RoundConverter {
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