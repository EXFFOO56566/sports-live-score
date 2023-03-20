package com.aroniez.futaa.repository

import androidx.lifecycle.LiveData
import com.aroniez.futaa.AppExecutors
import com.aroniez.futaa.api.BASE_URL
import com.aroniez.futaa.api.Resource
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.CompetitionsCallback
import com.aroniez.futaa.api.callbacks.LeaguesCallback
import com.aroniez.futaa.database.daos.LeagueDao
import com.aroniez.futaa.models.competitions.Competition
import com.aroniez.futaa.models.leagues.League

class LeaguesRepository(
        private val appExecutors: AppExecutors,
        private val leagueDao: LeagueDao
) {

    fun competitions(): LiveData<Resource<List<Competition>>> {
        return object : NetworkBoundResource<List<Competition>, CompetitionsCallback>(appExecutors) {
            override fun saveCallResult(item: CompetitionsCallback) {
                leagueDao.insertAll(item.data)
            }

            override fun shouldFetch(data: List<Competition>?) = true

            override fun loadFromDb() = leagueDao.getLeagues()

            override fun createCall() = RetrofitAdapter.createAPI(BASE_URL).leaguesLiveData()
        }.asLiveData()
    }

    companion object {
        @Volatile
        private var instance: LeaguesRepository? = null

        fun getInstance(appExecutors: AppExecutors, competitionsDao: LeagueDao) =
                instance ?: synchronized(this) {
                    instance
                            ?: LeaguesRepository(appExecutors, competitionsDao).also { instance = it }
                }
    }
}
