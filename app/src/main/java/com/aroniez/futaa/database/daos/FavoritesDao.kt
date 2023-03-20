package com.aroniez.futaa.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.aroniez.futaa.models.Favorite

@Dao
interface FavoritesDao : BaseDao<Favorite> {

    @Query("SELECT * FROM favorite")
    fun getFavoriteMatchesIds(): Array<Long>

    @Query("DELETE FROM favorite WHERE id = :fixtureId")
    fun removeMatchFromFavorites(fixtureId: Long)
}