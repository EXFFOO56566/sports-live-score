package com.aroniez.futaa.database.converters.goal

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.Assistant
import com.aroniez.futaa.models.fixture.Formation
import com.aroniez.futaa.models.fixture.Score
import com.aroniez.futaa.models.fixture.cards.Card
import com.aroniez.futaa.models.fixture.cards.CardData
import com.aroniez.futaa.models.fixture.goals.Goal
import com.aroniez.futaa.models.fixture.goals.GoalData
import com.aroniez.futaa.models.fixture.lineup.PlayerData
import com.aroniez.futaa.models.fixture.substitution.SubstitutionData
import com.aroniez.futaa.models.fixture.team.TeamData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class GoalListConverter {
    @TypeConverter
    fun fromString(value: String): ArrayList<Goal>? {
        val listType = object : TypeToken<ArrayList<Goal>>() {}.type
        return Gson().fromJson<ArrayList<Goal>>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: ArrayList<Goal>?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}