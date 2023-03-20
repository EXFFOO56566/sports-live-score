package com.aroniez.futaa.models.fixture.venue

import java.io.Serializable


class Venue(
        val id: Long,
        val name: String,
        val surface: String,
        val address: String,
        val city: String,
        val capacity: Long,
        val image_path: String,
        val coordinates: String
): Serializable