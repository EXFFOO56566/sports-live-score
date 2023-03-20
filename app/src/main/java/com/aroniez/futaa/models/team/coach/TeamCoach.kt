package com.aroniez.futaa.models.team.coach

import java.io.Serializable

class TeamCoach(
        val coach_id: Long,
        val team_id: Long,
        val country_id: Long,
        val common_name: String,
        val fullname: String,
        val firstname: String,
        val lastname: String,
        val nationality: String,
        val birthdate: String,
        val birthcountry: String,
        val image_path: String
) : Serializable