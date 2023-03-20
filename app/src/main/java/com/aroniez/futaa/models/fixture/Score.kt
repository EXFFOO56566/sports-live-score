package com.aroniez.futaa.models.fixture

import java.io.Serializable

class Score(
        val localteam_score: Int,
        val visitorteam_score: Int,
        val localteam_pen_score: String? = null,
        val et_score: String? = null,
        val ps_score: String? = null,
        val visitorteam_pen_score: String? = null,
        val ht_score: String,
        val ft_score: String
) : Serializable