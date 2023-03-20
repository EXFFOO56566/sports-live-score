package com.aroniez.futaa.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.ServerValue


class User {
    var uid: String? = ""
    var name: String? = null
    var email: String? = null
    var timestamp: Long? = 0

    override fun toString() = "$name"

    @Exclude
    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["timestamp"] = ServerValue.TIMESTAMP
        result["uid"] = uid!!
        return result
    }
}