package com.aroniez.futaa.database.converters.round

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.round.RoundData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class RoundDataConverter {
    @TypeConverter
    fun fromString(value: String): RoundData? {
        val listType = object : TypeToken<RoundData>() {}.type
        return Gson().fromJson<RoundData>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: RoundData?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}