package com.aroniez.futaa.database

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.Formation
import com.aroniez.futaa.models.fixture.Score
import com.aroniez.futaa.models.fixture.stats.StatsData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class StatsDataConverter {
    @TypeConverter
    fun fromString(value: String): StatsData? {
        val listType = object : TypeToken<StatsData>() {}.type
        return Gson().fromJson<StatsData>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: StatsData?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}