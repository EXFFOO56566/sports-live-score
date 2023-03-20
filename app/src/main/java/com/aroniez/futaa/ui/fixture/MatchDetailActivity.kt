package com.aroniez.futaa.ui.fixture

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.aroniez.futaa.AppExecutors
import com.aroniez.futaa.R
import com.aroniez.futaa.api.BASE_URL
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.MatchCallback
import com.aroniez.futaa.database.SoccerDatabase
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.utils.*
import com.facebook.ads.AdSettings
import com.google.android.material.tabs.TabLayout
import com.startapp.android.publish.adsCommon.StartAppAd
import com.startapp.android.publish.adsCommon.StartAppSDK
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.include_match_layout.*
import kotlinx.android.synthetic.main.include_viewpager_tab_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MatchDetailActivity : AppCompatActivity() {

    private var fixture: Fixture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.primary)
        }
        StartAppSDK.init(this, "204013588", false)
        StartAppAd.disableSplash()
        //StartAppAd.showAd(this)
        setContentView(R.layout.activity_match_detail)

        AdSettings.addTestDevice("568c05cf-c5ea-4bd3-b8d2-3957b4dce51f")

        val match = intent.getSerializableExtra("match") as Fixture
        fetchMatchDetailFromAPI(match.id)

        val matchAdapterBundle = MatchAdapterBundle(arrayListOf(), this, true)
        MatchUtil.initializeMatch(match, fixtureLayout, matchAdapterBundle)


        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        loadInterstialAds(this)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                loadLiveMatchesFromDb(match.id)
            }
        }, 0, 1000 * 15)

    }

    private fun loadLiveMatchesFromDb(matchId: Long) {
        AppExecutors().diskIO().execute {
            val dbInstance = SoccerDatabase.getInstance(this)
            val fixture = dbInstance.fixtureDao().getLiveMatchById(matchId)
            if (fixture != null) {
                AppExecutors().mainThread().execute {
                    val matchAdapterBundle = MatchAdapterBundle(arrayListOf(), this, true)
                    MatchUtil.initializeMatch(fixture, fixtureLayout, matchAdapterBundle)
                }
            }
        }
    }

    fun getMatchObject() = fixture

    private fun fetchMatchDetailFromAPI(fixtureId: Long) {
        showLoadingProgress(matchLayout)
        val callback = RetrofitAdapter.createAPI(BASE_URL).fixturesById(fixtureId)
        callback.enqueue(object : Callback<MatchCallback> {
            override fun onFailure(call: Call<MatchCallback>, t: Throwable) {
                showMessageLayout(getString(R.string.error_generic_message), matchLayout)
            }

            override fun onResponse(call: Call<MatchCallback>, response: Response<MatchCallback>) {
                hideLoadingProgress(matchLayout)
                if (response.isSuccessful) {
                    fixture = response.body()!!.data
                    supportActionBar!!.title = fixture!!.league!!.data.country.data.name + "::" + fixture!!.league!!.data.name

                    viewpager.adapter = MatchDetailFragmentsAdapter(supportFragmentManager, fixture!!.season_id, fixture!!.localteam_id, fixture!!.visitorteam_id)
                    tabLayout.setupWithViewPager(viewpager)
                    val scale = resources.displayMetrics.density
                    tabLayout.layoutParams.height = (36 * scale + 0.5F).toInt()
                    tabLayout.requestLayout()
                    tabLayout.tabMode = TabLayout.MODE_SCROLLABLE

                } else {
                    showMessageLayout(getString(R.string.error_generic_message), matchLayout)
                }
            }
        })
    }

}
