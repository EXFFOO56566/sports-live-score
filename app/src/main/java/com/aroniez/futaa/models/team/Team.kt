package com.aroniez.futaa.models.team

import androidx.annotation.Nullable
import com.aroniez.futaa.models.country.CountryData
import com.aroniez.futaa.models.fixture.FixturesData
import com.aroniez.futaa.models.fixture.venue.VenueData
import com.aroniez.futaa.models.seasons.players.SquadData
import com.aroniez.futaa.models.team.coach.CoachData
import com.aroniez.futaa.models.team.ranking.UefaRankingData
import com.aroniez.futaa.models.team.transfers.TransfersData
import java.io.Serializable


class Team(
        val id: Long,
        val legacy_id: Long,
        val country_id: Long,
        val venue_id: Long,
        val current_season_id: Long,
        val national_team: Boolean,
        val name: String,
        val founded: Int,
        val short_code: String,
        val logo_path: String,
        val twitter: String? = null,
        val country: CountryData,
        val coach: CoachData,
        val stats: CoachData,
        val venue: VenueData?,
        @Nullable val uefaranking: UefaRankingData?,
        val visitorFixtures: FixturesData,
        val localFixtures: FixturesData,
        val visitorResults: FixturesData,
        val localResults: FixturesData,
        val latest: FixturesData,
        val upcoming: FixturesData,
        val transfers: TransfersData?,
        val squad: SquadData
) : Serializable
