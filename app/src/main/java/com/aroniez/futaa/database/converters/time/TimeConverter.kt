package com.aroniez.futaa.database.converters.time

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.Formation
import com.aroniez.futaa.models.fixture.Score
import com.aroniez.futaa.models.fixture.color.Color
import com.aroniez.futaa.models.fixture.time.Time
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class TimeConverter {
    @TypeConverter
    fun fromString(value: String): Time? {
        val listType = object : TypeToken<Time>() {}.type
        return Gson().fromJson<Time>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: Time?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}