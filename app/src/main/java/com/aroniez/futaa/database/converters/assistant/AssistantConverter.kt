package com.aroniez.futaa.database.converters.assistant

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.Assistant
import com.aroniez.futaa.models.fixture.Formation
import com.aroniez.futaa.models.fixture.Score
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class AssistantConverter {
    @TypeConverter
    fun fromString(value: String): Assistant? {
        val listType = object : TypeToken<Assistant>() {}.type
        return Gson().fromJson<Assistant>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: Assistant): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}