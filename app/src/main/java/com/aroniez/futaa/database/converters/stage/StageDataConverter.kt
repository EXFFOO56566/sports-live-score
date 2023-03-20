package com.aroniez.futaa.database.converters.stage

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.Assistant
import com.aroniez.futaa.models.fixture.Formation
import com.aroniez.futaa.models.fixture.Score
import com.aroniez.futaa.models.fixture.goals.GoalData
import com.aroniez.futaa.models.fixture.lineup.PlayerData
import com.aroniez.futaa.models.fixture.stage.StageData
import com.aroniez.futaa.models.fixture.substitution.SubstitutionData
import com.aroniez.futaa.models.fixture.team.TeamData
import com.aroniez.futaa.models.leagues.LeagueData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class StageDataConverter {
    @TypeConverter
    fun fromString(value: String): StageData? {
        val listType = object : TypeToken<StageData>() {}.type
        return Gson().fromJson<StageData>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: StageData?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}