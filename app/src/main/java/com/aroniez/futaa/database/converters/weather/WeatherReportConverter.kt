package com.aroniez.futaa.database.converters.weather

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.weather.WeatherReport
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class WeatherReportConverter {
    @TypeConverter
    fun fromString(value: String): WeatherReport? {
        val listType = object : TypeToken<WeatherReport>() {}.type
        return Gson().fromJson<WeatherReport>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: WeatherReport?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}