package com.aroniez.futaa.models

class MatchPreviewItem {
    var type: String? = "" //event could be goal, card or substitution
    var match_team: String? = "" //either home of away
    var time: Int? = 0
    var display_time: String? = "1"

    var player_name: String? = ""
    var score: String? = ""
    var player_assist_name: String? = ""

    var card_player: String? = ""
    var card: String? = ""

    var substitution_type: String? = ""
    var player_in: String? = ""
    var player_out: String? = ""
}