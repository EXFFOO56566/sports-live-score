package com.aroniez.futaa.database.converters.color

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.Formation
import com.aroniez.futaa.models.fixture.Score
import com.aroniez.futaa.models.fixture.color.Color
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ColorConverter {
    @TypeConverter
    fun fromString(value: String): Color? {
        val listType = object : TypeToken<Color>() {}.type
        return Gson().fromJson<Color>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: Color?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}