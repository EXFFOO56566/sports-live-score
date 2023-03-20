package com.aroniez.futaa.api


const val API_KEY = "Z39FH61yZfJ6pYtOCFlsvzTk3892S6xvvTqgP0NGxzH8jZQpr4UKedvmtbpr"
const val googleTranslateKey = "TRANSALTE_KEY"

const val arabicLang = "ar"
const val englishLang = "en"
const val selectedLang = englishLang

const val BASE_URL = "https://soccer.sportmonks.com/api/v2.0/"
const val VIDEO_URL = "https://www.scorebat.com/video-api/"
const val NEWS_URL = "http://livesoccertv.com/mobile/api/"

const val YESTERDAY_MATCHES = "yesterday"
const val TODAY_MATCHES = "today"
const val TOMORROW_MATCHES = "tomorrow"

const val RESULTS_MATCHES = "results"
const val CURRENT_MATCHES = "current"
const val UPCOMING_MATCHES = "upcoming"

const val LIVE_MATCHES = "live"
const val FAVORITE_MATCHES = "favorites"

const val queryLimit = 30
private const val playersQueryLimit = 20
const val limit = ":limit($queryLimit|1)"

const val playerLimit = ":limit($playersQueryLimit|1)"

const val chatsBatchCount = 20

val dirtyWords = arrayOf(
        "bad_words"
)
