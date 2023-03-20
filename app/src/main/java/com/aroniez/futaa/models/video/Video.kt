package com.aroniez.futaa.models.video

import java.io.Serializable

data class Video(
        var title: String,
        var embed: String? = ""
):Serializable