package com.aroniez.futaa.ui.fixture

import android.content.Context
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.aroniez.futaa.models.leagues.LeagueRoundHeader
import com.aroniez.futaa.utils.TranslatorUtil
import com.google.cloud.translate.Translate

class LeagueMatchesBundle(
        val leagues: ArrayList<LeagueRoundHeader>,
        val context: Context,
        val shouldOpenTeamDetail: Boolean
) {
    var translator: Translate = TranslatorUtil.getTranslateService(context)!!
    var favoriteMatchesIds: Array<Long> = arrayOf()
    var recyclerView: RecyclerView? = null
    var scrollView: NestedScrollView? = null
}