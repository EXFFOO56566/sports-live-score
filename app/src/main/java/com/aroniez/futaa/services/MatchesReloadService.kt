package com.aroniez.futaa.services

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import com.aroniez.futaa.AppExecutors
import com.aroniez.futaa.api.BASE_URL
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.MatchesCallback
import com.aroniez.futaa.database.SoccerDatabase
import com.aroniez.futaa.notification.NotificationUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MatchesReloadService : Service() {
    private val binder = LocalBinder()
    override fun onBind(intent: Intent?) = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        val context = this@MatchesReloadService
        AppExecutors().diskIO().execute {
            val soccerDatabase = SoccerDatabase.getInstance(this)
            val favorites = soccerDatabase.favoritesDao().getFavoriteMatchesIds()
            Timer().scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    if (favorites.isNotEmpty()) {
                        if(context!=null){
                            refreshGames(this@MatchesReloadService, soccerDatabase)
                        }
                    } else {
                        if (isAppRunningInForeground()) {
                            if(context!=null){
                                refreshGames(this@MatchesReloadService, soccerDatabase)
                            }
                        }
                    }
                }
            }, 0, 1000 * 30)
        }
    }

    fun isAppRunningInForeground(): Boolean {
        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningProcesses = am.runningAppProcesses
        if (runningProcesses != null) {
            for (processInfo in runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (activeProcess in processInfo.pkgList) {
                        if (activeProcess == packageName) {
                            //If your app is the process in foreground, then it's not in running in background
                            return true
                        }
                    }
                }
            }
        }
        return false
    }


    private fun refreshGames(context: Context, soccerDatabase: SoccerDatabase) {
        val callback = RetrofitAdapter.createAPI(BASE_URL).livescoresNow()
        callback.enqueue(object : Callback<MatchesCallback> {
            override fun onFailure(call: Call<MatchesCallback>, t: Throwable) {
            }

            override fun onResponse(call: Call<MatchesCallback>, response: Response<MatchesCallback>) {
                if (response.isSuccessful) {
                    AppExecutors().diskIO().execute {
                        val favoritesDao = soccerDatabase.favoritesDao()
                        val fixturesDao = soccerDatabase.fixtureDao()
                        val oldData = fixturesDao.getLiveMatches()
                        val newData = response.body()!!.data
                        fixturesDao.deleteAllMatches()
                        fixturesDao.insertAll(newData)

                        val favoriteMatches = favoritesDao.getFavoriteMatchesIds()

                        if (oldData.isNotEmpty() && newData.isNotEmpty()) {
                            for (oldFixture in oldData) {
                                val newFixture = newData.first { it.id == oldFixture.id }
                                val notificationTitle = oldFixture.localTeam.data.name + " vs " + oldFixture.visitorTeam.data.name
                                if (oldFixture.scores.localteam_score != newFixture.scores.localteam_score
                                        && newFixture.scores.localteam_score > oldFixture.scores.localteam_score) {

                                    //check if match is favorite, if yes, show notifications
                                    if (favoriteMatches.contains(oldFixture.localteam_id)) {
                                        val score = "Goal [" + newFixture.scores.localteam_score + "] - " + newFixture.scores.visitorteam_score + " " + oldFixture.localTeam.data.name
                                        NotificationUtil.showGoalNotification(context, oldFixture, notificationTitle, score, Intent())
                                    }
                                }

                                if (oldFixture.scores.visitorteam_score != newFixture.scores.visitorteam_score
                                        && newFixture.scores.visitorteam_score > oldFixture.scores.visitorteam_score) {

                                    if (favoriteMatches.contains(oldFixture.visitorteam_id)) {
                                        val score = "Goal " + newFixture.scores.localteam_score + " - [" + newFixture.scores.visitorteam_score + "]" + oldFixture.visitorTeam.data.name
                                        NotificationUtil.showGoalNotification(context, oldFixture, notificationTitle, score, Intent())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    inner class LocalBinder : Binder() {
        fun getService(): MatchesReloadService = this@MatchesReloadService
    }

}