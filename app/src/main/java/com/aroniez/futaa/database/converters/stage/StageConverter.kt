package com.aroniez.futaa.database.converters.stage

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.stage.Stage
import com.aroniez.futaa.models.leagues.League
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class StageConverter {
    @TypeConverter
    fun fromString(value: String): Stage? {
        val listType = object : TypeToken<Stage>() {}.type
        return Gson().fromJson<Stage>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: Stage?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}