package com.aroniez.futaa.utils

import android.widget.ImageView
import android.widget.Toast
import com.aroniez.futaa.AppExecutors
import com.aroniez.futaa.R
import com.aroniez.futaa.database.SoccerDatabase
import com.aroniez.futaa.models.Favorite
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.ui.fixture.MatchAdapterBundle

object FavoriteUtil {
    fun addMatchToFavorites(fixture: Fixture, matchAdapterBundle: MatchAdapterBundle, imageView: ImageView) {
        AppExecutors().diskIO().execute {
            val favoritesDao = SoccerDatabase.getInstance(matchAdapterBundle.context).favoritesDao()
            val fxturesDao = SoccerDatabase.getInstance(matchAdapterBundle.context).fixtureDao()
            if (matchAdapterBundle.favoriteMatchesIds.contains(fixture.id)) {
                favoritesDao.removeMatchFromFavorites(fixture.id)
                fxturesDao.removeMatchFromFavorites(fixture.id)
                AppExecutors().mainThread().execute {
                    imageView.setImageResource(R.drawable.ic_star_outline_grey600_24dp)
//                    val fragmentManager = (matchAdapterBundle.context as MainActivity).supportFragmentManager
//                    val fragment = fragmentManager.findFragmentByTag("three") as FavoriteMatchesFragment
//                    fragmentManager.beginTransaction().detach(fragment).attach(fragment).commit()
                }
            } else {
                favoritesDao.insert(Favorite(fixture.id))
                fxturesDao.insert(fixture)
                AppExecutors().mainThread().execute {
                    Toast.makeText(matchAdapterBundle.context, "Match added to favorites", Toast.LENGTH_LONG).show()
                    imageView.setImageResource(R.drawable.ic_star_grey600_24dp)
                }
            }
        }
    }
}