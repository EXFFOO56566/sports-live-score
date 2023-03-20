package com.aroniez.futaa.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.aroniez.futaa.models.Favorite
import com.aroniez.futaa.models.competitions.Competition
import com.aroniez.futaa.models.leagues.League

@Dao
interface LeagueDao : BaseDao<Competition> {

    @Query("SELECT * FROM competitions")
    fun getLeagues(): LiveData<List<Competition>>

    @Query("SELECT * FROM favorite")
    fun getFavoriteMatchesIds(): Array<Long>

    @Query("DELETE FROM favorite WHERE id = :fixtureId")
    fun removeMatchFromFavorites(fixtureId: Long)
}