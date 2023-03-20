package com.aroniez.futaa.models.players

import java.io.Serializable


class Player(
        val player_id: Int,
        val team_id: Int,
        val country_id: Int,
        val position_id: Int,
        val common_name: String,
        val fullname: String,
        val firstname: String,
        val lastname: String,
        val nationality: String,
        val birthdate: String,
        val birthcountry: String,
        val birthplace: String,
        val height: String,
        val weight: String,
        val image_path: String
) : Serializable