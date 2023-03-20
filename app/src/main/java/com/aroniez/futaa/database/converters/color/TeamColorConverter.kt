package com.aroniez.futaa.database.converters.color

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.color.TeamColor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class TeamColorConverter {
    @TypeConverter
    fun fromString(value: String): TeamColor? {
        val listType = object : TypeToken<TeamColor>() {}.type
        return Gson().fromJson<TeamColor>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: TeamColor): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}