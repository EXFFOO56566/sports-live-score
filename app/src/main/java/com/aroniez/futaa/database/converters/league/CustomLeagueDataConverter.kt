package com.aroniez.futaa.database.converters.league

import androidx.room.TypeConverter
import com.aroniez.futaa.models.leagues.CustomLeagueData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CustomLeagueDataConverter {
    @TypeConverter
    fun fromString(value: String): CustomLeagueData? {
        val listType = object : TypeToken<CustomLeagueData>() {}.type
        return Gson().fromJson<CustomLeagueData>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: CustomLeagueData?): String? {
        val gson = Gson()
        return if (stringList != null) {
            gson.toJson(stringList)
        } else {
            gson.toJson("")
        }
    }
}