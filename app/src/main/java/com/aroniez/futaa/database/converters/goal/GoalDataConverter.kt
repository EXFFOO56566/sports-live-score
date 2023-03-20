package com.aroniez.futaa.database.converters.goal

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.Assistant
import com.aroniez.futaa.models.fixture.Formation
import com.aroniez.futaa.models.fixture.Score
import com.aroniez.futaa.models.fixture.goals.GoalData
import com.aroniez.futaa.models.fixture.lineup.PlayerData
import com.aroniez.futaa.models.fixture.substitution.SubstitutionData
import com.aroniez.futaa.models.fixture.team.TeamData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class GoalDataConverter {
    @TypeConverter
    fun fromString(value: String): GoalData? {
        val listType = object : TypeToken<GoalData>() {}.type
        return Gson().fromJson<GoalData>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: GoalData?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}