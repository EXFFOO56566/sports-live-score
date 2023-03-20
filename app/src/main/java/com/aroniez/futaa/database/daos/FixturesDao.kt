package com.aroniez.futaa.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.aroniez.futaa.models.fixture.Fixture

@Dao
interface FixturesDao : BaseDao<Fixture> {

    @Query("DELETE FROM fixtures WHERE id = :fixtureId")
    fun removeMatchFromFavorites(fixtureId: Long)

    @Query("SELECT * FROM fixtures ORDER BY id DESC")
    fun getAllFavoriteMatches(): LiveData<List<Fixture>>

    @Query("SELECT * FROM fixtures WHERE time_status ='LIVE' OR time_status ='HT' ORDER BY id DESC")
    fun getLiveFavoriteMatches(): List<Fixture>


    @Query("SELECT * FROM fixtures ORDER BY id DESC")
    fun getLiveMatchesLive(): LiveData<List<Fixture>>

    @Query("SELECT * FROM fixtures ORDER BY id DESC")
    fun getLiveMatches(): List<Fixture>

    @Query("SELECT * FROM fixtures WHERE id=:matchId ORDER BY id DESC")
    fun getLiveMatchById(matchId: Long): Fixture

    @Query("DELETE FROM fixtures WHERE id = :fixtureId")
    fun deleteMatchById(fixtureId: String)

    @Query("DELETE FROM fixtures")
    fun deleteAllMatches()

    @Query("SELECT * FROM fixtures WHERE id = :fixtureId")
    fun getFixture(fixtureId: String): Fixture
}