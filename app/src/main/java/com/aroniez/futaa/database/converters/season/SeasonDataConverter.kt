package com.aroniez.futaa.database.converters.season

import androidx.room.TypeConverter
import com.aroniez.futaa.models.seasons.SeasonData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SeasonDataConverter {
    @TypeConverter
    fun fromString(value: String): SeasonData? {
        val listType = object : TypeToken<SeasonData>() {}.type
        return Gson().fromJson<SeasonData>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: SeasonData?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}