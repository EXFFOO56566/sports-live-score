package com.aroniez.futaa.database.converters.weather

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.weather.Temperature
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class TemperatureConverter {
    @TypeConverter
    fun fromString(value: String): Temperature? {
        val listType = object : TypeToken<Temperature>() {}.type
        return Gson().fromJson<Temperature>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: Temperature): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}