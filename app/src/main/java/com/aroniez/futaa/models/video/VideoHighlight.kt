package com.aroniez.futaa.models.video

import java.io.Serializable

data class VideoHighlight(
        var title: String,
        var embed: String? = "",
        var url: String? = "",
        var thumbnail: String? = "",
        var date: String? = "",
        var side1: Side? = null,
        var side2: Side? = null,
        var competition: Competition? = null,
        var videos: List<Video>? = arrayListOf()
): Serializable