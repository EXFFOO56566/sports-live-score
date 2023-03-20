package com.aroniez.futaa.database.converters.player

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.lineup.PlayersData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class PlayerDataConverter {
    @TypeConverter
    fun fromString(value: String): PlayersData? {
        val listType = object : TypeToken<PlayersData>() {}.type
        return Gson().fromJson<PlayersData>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: PlayersData?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}