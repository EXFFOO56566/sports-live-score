package com.aroniez.futaa.ui.season

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.aroniez.futaa.R
import com.aroniez.futaa.models.leagues.Coverage
import com.aroniez.futaa.utils.loadInterstialAds
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_league_detail1.*
import kotlinx.android.synthetic.main.include_viewpager_tabs_layout.*


class LeagueDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.primary)
        }
        setContentView(R.layout.activity_league_detail1)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""

        val hasStanding = intent.getBooleanExtra("live_standings", false)
        val logoPath = intent.getStringExtra("logo_path")
        val countryName = intent.getStringExtra("country_name")
        val leagueName = intent.getStringExtra("league_name")

        Picasso.get().load(logoPath).placeholder(R.drawable.goals).into(leagueImage)
        league_name.text = "$countryName $leagueName"


        viewpager.adapter = LeagueDetailsFragmentsAdapter(supportFragmentManager, hasStanding, getSeasonId())
        tabLayout.setupWithViewPager(viewpager)
        //tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.primary))
        //tabLayout.tabTextColors = ContextCompat.getColorStateList(this, R.color.primary)
        tabLayout.setBackgroundColor(resources.getColor(R.color.primary_dark))
        viewpager.offscreenPageLimit = 2
        viewpagerTabsLayout.visibility = View.VISIBLE

        loadInterstialAds(this)
    }

    fun getSeasonId() = intent.getLongExtra("season_id", 0L)
    fun getSeasonCoverage() = intent.getSerializableExtra("coverage") as Coverage

}
