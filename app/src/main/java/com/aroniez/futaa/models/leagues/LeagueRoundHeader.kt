package com.aroniez.futaa.models.leagues

import com.aroniez.futaa.models.fixture.Fixture
import com.bignerdranch.expandablerecyclerview.model.Parent
import java.io.Serializable

class LeagueRoundHeader(
        var round: String? = "",
        var fixtures: List<Fixture>? = null
) : Serializable, Parent<Fixture> {
    override fun getChildList() = fixtures

    override fun isInitiallyExpanded() = true
}