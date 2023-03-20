package com.aroniez.futaa.repository

import androidx.lifecycle.LiveData
import com.aroniez.futaa.AppExecutors
import com.aroniez.futaa.api.BASE_URL
import com.aroniez.futaa.api.Resource
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.MatchesCallback
import com.aroniez.futaa.database.daos.FixturesDao
import com.aroniez.futaa.models.fixture.Fixture

class FixturesRepository(
        private val appExecutors: AppExecutors,
        private val fixturesDao: FixturesDao
) {

    fun favorites(): LiveData<Resource<List<Fixture>>> {
        return object : NetworkBoundResource<List<Fixture>, MatchesCallback>(appExecutors) {
            override fun saveCallResult(item: MatchesCallback) {
                fixturesDao.insertAll(item.data)
            }

            override fun shouldFetch(data: List<Fixture>?) = false

            override fun loadFromDb() = fixturesDao.getAllFavoriteMatches()

            override fun createCall() = RetrofitAdapter.createAPI(BASE_URL).fixturesByIdLive("")
        }.asLiveData()
    }

    companion object {
        @Volatile
        private var instance: FixturesRepository? = null

        fun getInstance(appExecutors: AppExecutors, fixturesDao: FixturesDao) =
                instance ?: synchronized(this) {
                    instance
                            ?: FixturesRepository(appExecutors, fixturesDao).also { instance = it }
                }
    }
}
