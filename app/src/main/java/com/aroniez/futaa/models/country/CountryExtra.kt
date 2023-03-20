package com.aroniez.futaa.models.country

import java.io.Serializable


class CountryExtra(
        val continent: String,
        val sub_region: String,
        val world_region: String,
        val fifa: String,
        val iso: String,
        val iso2: String,
        val longitude: String,
        val latitude: String,
        val flag: String
) : Serializable
