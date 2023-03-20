package com.aroniez.futaa.database.converters.formation

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.Formation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class FormationConverter {
    @TypeConverter
    fun fromString(value: String): Formation? {
        val listType = object : TypeToken<Formation>() {}.type
        return Gson().fromJson<Formation>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: Formation?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}