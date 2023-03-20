package com.aroniez.futaa.models.country

import com.aroniez.futaa.models.competitions.Competition
import com.bignerdranch.expandablerecyclerview.model.Parent
import java.io.Serializable


class Country(
        val id: Int,
        val name: String,
        val image_path: String,
        var leagues: CompetitionsData,
        val extra: CountryExtra
) : Serializable, Parent<Competition> {
    override fun getChildList() = leagues.data

    override fun isInitiallyExpanded() = true
}
