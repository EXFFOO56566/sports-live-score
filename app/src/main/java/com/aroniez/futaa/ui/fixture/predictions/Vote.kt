package com.aroniez.futaa.ui.fixture.predictions

import com.google.firebase.database.Exclude
import com.google.firebase.database.ServerValue


class Vote {
    var id: String? = ""
    var timestamp: Long = 0
    var matchTitle: String? = ""
    var vote: String? = ""
    var matchId: Long? = 0
    var senderUsername: String? = ""


    @Exclude
    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["timestamp"] = ServerValue.TIMESTAMP
        result["matchTitle"] = matchTitle!!
        result["matchId"] = matchId!!
        result["vote"] = vote!!
        result["senderUsername"] = senderUsername!!

        return result
    }
}