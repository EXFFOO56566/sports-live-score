package com.aroniez.futaa.models.video

import java.io.Serializable

data class Competition(
        var name: String? = "",
        var id: Int? = 0,
        var url: String? = ""
):Serializable