package com.aroniez.futaa.models.competitions

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aroniez.futaa.models.country.CountryData
import com.aroniez.futaa.models.fixture.round.Round
import com.aroniez.futaa.models.fixture.stage.Stage
import com.aroniez.futaa.models.leagues.Coverage
import com.aroniez.futaa.models.seasons.Season
import com.aroniez.futaa.models.seasons.SeasonData
import java.io.Serializable

@Entity(tableName = "competitions")
class Competition(
        @PrimaryKey val id: Long,
        val active: Boolean,
        val is_cup: Boolean,
        val live_standings: Boolean,
        val type: String,
        val legacy_id: Int,
        val country_id: Int,
        val current_season_id: Long,
        val current_round_id: Long,
        val current_stage_id: Long,
        val logo_path: String? = null,
        val name: String,
        val coverage: Coverage,
        var round: Round?,
        var season: SeasonData?,
        val country: CountryData
): Serializable