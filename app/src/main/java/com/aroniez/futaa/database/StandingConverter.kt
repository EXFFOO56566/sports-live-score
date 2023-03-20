package com.aroniez.futaa.database

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.Standing
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class StandingConverter {
    @TypeConverter
    fun fromString(value: String): Standing? {
        val listType = object : TypeToken<Standing>() {}.type
        return Gson().fromJson<Standing>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: Standing?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}