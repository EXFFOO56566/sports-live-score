package com.aroniez.futaa.database.converters.league

import androidx.room.TypeConverter
import com.aroniez.futaa.models.leagues.League
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class LeagueCoverageConverter {
    @TypeConverter
    fun fromString(value: String): League? {
        val listType = object : TypeToken<League>() {}.type
        return Gson().fromJson<League>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: League?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}