package com.aroniez.futaa.database

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.Formation
import com.aroniez.futaa.models.fixture.Score
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ScoreConverter {
    @TypeConverter
    fun fromString(value: String): Score? {
        val listType = object : TypeToken<Score>() {}.type
        return Gson().fromJson<Score>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: Score?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}