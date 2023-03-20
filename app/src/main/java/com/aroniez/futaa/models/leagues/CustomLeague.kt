package com.aroniez.futaa.models.leagues

import com.aroniez.futaa.models.country.CountryData
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.models.fixture.round.Round
import com.bignerdranch.expandablerecyclerview.model.Parent
import java.io.Serializable

class CustomLeague(
        val id: Long,
        val active: Boolean,
        val is_cup: Boolean,
        val live_standings: Boolean,
        val type: String,
        val legacy_id: Int,
        val country_id: Int,
        val current_season_id: Long,
        val current_round_id: Long,
        val current_stage_id: Long,
        val logo_path: String,
        val name: String,
        val coverage: Coverage,
        val country: CountryData,
        var round: Round?,
        var fixtures: List<Fixture>? = null
) : Serializable, Parent<Fixture> {
    override fun getChildList() = fixtures

    override fun isInitiallyExpanded() = true
}