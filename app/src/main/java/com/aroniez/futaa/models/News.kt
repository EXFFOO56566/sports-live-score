package com.aroniez.futaa.models

data class News(
        var article_id: String,
        var status: String? = "",
        var title: String? = "",
        var slug: String? = "",
        var content: String? = "",
        var publish_date: String? = "",
        var timestamp: String? = "",
        var cdn_image: String? = ""
)