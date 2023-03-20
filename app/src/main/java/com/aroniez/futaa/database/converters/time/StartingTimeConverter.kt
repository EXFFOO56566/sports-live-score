package com.aroniez.futaa.database.converters.time

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.time.StartingAt
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class StartingTimeConverter {
    @TypeConverter
    fun fromString(value: String): StartingAt? {
        val listType = object : TypeToken<StartingAt>() {}.type
        return Gson().fromJson<StartingAt>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: StartingAt?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}