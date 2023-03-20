package com.aroniez.futaa.models.fixture

import androidx.annotation.Nullable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aroniez.futaa.models.fixture.cards.CardData
import com.aroniez.futaa.models.fixture.color.Color
import com.aroniez.futaa.models.fixture.goals.GoalData
import com.aroniez.futaa.models.fixture.lineup.PlayersData
import com.aroniez.futaa.models.fixture.round.RoundData
import com.aroniez.futaa.models.fixture.stage.StageData
import com.aroniez.futaa.models.fixture.stats.StatsData
import com.aroniez.futaa.models.fixture.substitution.SubstitutionData
import com.aroniez.futaa.models.fixture.team.TeamData
import com.aroniez.futaa.models.fixture.time.Time
import com.aroniez.futaa.models.fixture.venue.VenueData
import com.aroniez.futaa.models.fixture.weather.WeatherReport
import com.aroniez.futaa.models.leagues.CustomLeague
import com.aroniez.futaa.models.leagues.CustomLeagueData
import java.io.Serializable

@Entity(tableName = "fixtures")
data class Fixture(
        @PrimaryKey
        val id: Long,
        val league_id: Long,
        val season_id: Long,
        val stage_id: Long,
        val round_id: Long,
        val group_id: Long,
        val aggregate_id: Long,
        val venue_id: Long,
        val referee_id: Long,
        val localteam_id: Long,
        val visitorteam_id: Long,
        val winner_team_id: Long,
        val weather_report: WeatherReport? = null,
        val commentaries: Boolean,
        val attendance: Int,
        val pitch: String? = null,
        val details: String? = null,
        val neutral_venue: Boolean,
        val winning_odds_calculated: Boolean,
        val formations: Formation,
        val scores: Score,
        @Embedded(prefix = "time_")
        val time: Time,
        val coaches: Coach,
        val standings: Standing,
        val assistants: Assistant,
        val leg: String,
        @Nullable val colors: Color?,
        val deleted: Boolean,

        val localTeam: TeamData,
        val visitorTeam: TeamData,
        @Nullable val lineup: PlayersData?,

        @Nullable val round: RoundData?,
        @Nullable val stage: StageData?,

        @Nullable val substitutions: SubstitutionData?,
        @Nullable val goals: GoalData?,
        @Nullable val cards: CardData?,

        @Nullable val venue: VenueData?,
        @Nullable val league: CustomLeagueData?,
        @Nullable val customLeague: CustomLeague?,
        @Nullable val stats: StatsData?
) : Serializable
