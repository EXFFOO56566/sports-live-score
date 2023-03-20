package com.aroniez.futaa.ui.teams

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import com.aroniez.futaa.R
import com.aroniez.futaa.events.OnTeamFixturesLoaded
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.models.fixture.color.TeamColor
import com.aroniez.futaa.models.team.Team
import com.aroniez.futaa.ui.teams.fixtures.TeamFixturesFragment
import com.aroniez.futaa.ui.teams.overview.TeamOverviewFragment
import com.aroniez.futaa.ui.teams.results.TeamResultsFragment
import com.aroniez.futaa.utils.loadInterstialAds
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_league_detail1.*
import kotlinx.android.synthetic.main.include_viewpager_tabs_layout.*


class TeamDetailActivity : AppCompatActivity(), OnTeamFixturesLoaded {

    private var teamOverview: Team? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.primary)
        }
        setContentView(R.layout.activity_league_detail1)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""

        loadInterstialAds(this)

        val team = intent.getSerializableExtra("team") as Team
        loadData()
        val colors = intent.getSerializableExtra("colors") as TeamColor?
        if (colors != null) {
            if (colors.color != null) {
                Log.d("BugTracer", "team color is not null: " + colors.color)
                appBarLayout.setBackgroundColor(Color.parseColor(colors.color))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val window = window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = Color.parseColor(colors.color)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.navigationBarColor = Color.parseColor(colors.color)
                    }
                }
                if (ColorUtils.calculateLuminance(Color.parseColor(colors.color)) < 0.5) {
                    //this is dark color
                    league_name.setTextColor(resources.getColor(R.color.white))
                } else {
                    //this is light color
                    league_name.setTextColor(resources.getColor(R.color.black))
                }
            }else{
            Log.d("BugTracer", "team color is null: 1")
            }
        }else{
            Log.d("BugTracer", "team color is null: 2")
        }

        Picasso.get().load(team.logo_path).placeholder(R.drawable.goals).into(leagueImage)
        league_name.text = team.name
    }

    private fun loadData() {
        //country_name.text = response.body()!!.data.country.data.name
        viewpager.adapter = TeamDetailsFragmentsAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewpager)
        viewpager.offscreenPageLimit = 4
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE

        val colors = intent.getSerializableExtra("colors") as TeamColor?
        initializeTabs(colors)

        //tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.indicator));
        viewpagerTabsLayout.visibility = View.VISIBLE
        //viewpager.cur rentItem = 0
    }

    fun initializeTabs(colors: TeamColor?) {
        if (colors != null) {
            if (colors.color != null) {
                //tabLayout.setBackgroundColor(Color.parseColor(colors.color))
                if (ColorUtils.calculateLuminance(Color.parseColor(colors.color)) < 0.5) {
                    //tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white))
                    //tabLayout.tabTextColors = ContextCompat.getColorStateList(this@TeamDetailActivity, R.color.white)
                    //tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this@TeamDetailActivity, R.color.white))
                } else {
                    //tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.primary))
                    //tabLayout.tabTextColors = ContextCompat.getColorStateList(this@TeamDetailActivity, R.color.primary)
                    //tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this@TeamDetailActivity, R.color.primary))
                }
            } else {
                //tabLayout.setBackgroundColor(resources.getColor(R.color.white))
                //tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.primary))
                //tabLayout.tabTextColors = ContextCompat.getColorStateList(this@TeamDetailActivity, R.color.primary)
            }
        } else {
            //tabLayout.setBackgroundColor(resources.getColor(R.color.white))
            //tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.primary))
            //tabLayout.tabTextColors = ContextCompat.getColorStateList(this@TeamDetailActivity, R.color.primary)
        }
    }


    fun getTeam() = intent.getSerializableExtra("team") as Team

    fun getTeamOverview() = teamOverview

    override fun onFixtureLoaded(fixture: Fixture, isNextMatch: Boolean) {
        val overviewFragment = TeamOverviewFragment()
        overviewFragment.displayFixture(fixture, isNextMatch, this, fixtureLayout)
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        if (fragment is TeamResultsFragment) {
            fragment.setOnTeamFixturesLoaded(this)
        }
        if (fragment is TeamFixturesFragment) {
            fragment.setOnTeamFixturesLoaded(this)
        }
    }

}
