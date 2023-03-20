package com.aroniez.futaa.utils

import android.content.Context
import com.aroniez.futaa.AppExecutors
import com.aroniez.futaa.database.SoccerDatabase
import com.aroniez.futaa.repository.FixturesRepository
import com.aroniez.futaa.repository.LeaguesRepository
import com.aroniez.futaa.ui.viewholders.FixturesViewModelFactory
import com.aroniez.futaa.ui.viewholders.LeaguesViewModelFactory

object InjectorUtils {

    private fun getFixturesRepo(c: Context) = FixturesRepository.getInstance(AppExecutors(), SoccerDatabase.getInstance(c).fixtureDao())
    private fun getCompetitionsRepo(c: Context) = LeaguesRepository.getInstance(AppExecutors(), SoccerDatabase.getInstance(c).leaguesDao())

    fun provideFixturesViewModelFactory(context: Context) = FixturesViewModelFactory(getFixturesRepo(context))
    fun provideLeaguesViewModelFactory(context: Context) = LeaguesViewModelFactory(getCompetitionsRepo(context))

}