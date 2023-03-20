package com.aroniez.futaa.database

import androidx.room.TypeConverter
import com.aroniez.futaa.models.fixture.Assistant
import com.aroniez.futaa.models.fixture.Formation
import com.aroniez.futaa.models.fixture.Score
import com.aroniez.futaa.models.fixture.goals.GoalData
import com.aroniez.futaa.models.fixture.lineup.PlayerData
import com.aroniez.futaa.models.fixture.substitution.SubstitutionData
import com.aroniez.futaa.models.fixture.team.TeamData
import com.aroniez.futaa.models.fixture.venue.VenueData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class VenueDataConverter {
    @TypeConverter
    fun fromString(value: String): VenueData? {
        val listType = object : TypeToken<VenueData>() {}.type
        return Gson().fromJson<VenueData>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: VenueData?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}