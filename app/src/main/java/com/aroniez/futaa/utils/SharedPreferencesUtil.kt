package com.aroniez.futaa.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtil {
    private const val USER_PREF_BOOLEAN_IS_LOGGED_IN = "isUserLoggedIn"
    private const val USER_PREF_STRING_USERNAME = "username"
    private const val USER_PREF_NAME = "user"

    private fun getUserSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(USER_PREF_NAME, Context.MODE_PRIVATE)
    }

    fun checkIfUserLoggedInFromPrefs(context: Context): Boolean {
        return getUserSharedPreference(context).getBoolean(USER_PREF_BOOLEAN_IS_LOGGED_IN, false)
    }

    fun saveUserLoggedInToPrefs(context: Context, isUserLoggedIn: Boolean) {
        getUserSharedPreference(context).edit().putBoolean(USER_PREF_BOOLEAN_IS_LOGGED_IN, isUserLoggedIn).apply()
    }

    fun getUsername(context: Context): String {
        return getUserSharedPreference(context).getString(USER_PREF_STRING_USERNAME, "futaa_user")!!
    }

    fun saveUsername(context: Context, username: String) {
        getUserSharedPreference(context).edit().putString(USER_PREF_STRING_USERNAME, username).apply()
    }

}