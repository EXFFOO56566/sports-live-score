package com.aroniez.futaa.database.converters.league

import androidx.room.TypeConverter
import com.aroniez.futaa.models.leagues.Coverage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CoverageConverter {
    @TypeConverter
    fun fromString(value: String): Coverage? {
        val listType = object : TypeToken<Coverage>() {}.type
        return Gson().fromJson<Coverage>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: Coverage?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}