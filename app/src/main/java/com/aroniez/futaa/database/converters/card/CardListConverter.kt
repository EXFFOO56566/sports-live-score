package com.aroniez.futaa.database.converters.card

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.Assistant
import com.aroniez.futaa.models.fixture.Formation
import com.aroniez.futaa.models.fixture.Score
import com.aroniez.futaa.models.fixture.cards.Card
import com.aroniez.futaa.models.fixture.cards.CardData
import com.aroniez.futaa.models.fixture.goals.GoalData
import com.aroniez.futaa.models.fixture.lineup.PlayerData
import com.aroniez.futaa.models.fixture.substitution.SubstitutionData
import com.aroniez.futaa.models.fixture.team.TeamData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CardListConverter {
    @TypeConverter
    fun fromString(value: String): ArrayList<Card>? {
        val listType = object : TypeToken<ArrayList<Card>>() {}.type
        return Gson().fromJson<ArrayList<Card>>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: ArrayList<Card>?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}