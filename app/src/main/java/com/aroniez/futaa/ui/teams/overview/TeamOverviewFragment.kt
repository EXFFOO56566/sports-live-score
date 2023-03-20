package com.aroniez.futaa.ui.teams.overview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aroniez.futaa.R
import com.aroniez.futaa.api.BASE_URL
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.TeamCallback
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.models.team.Team
import com.aroniez.futaa.ui.teams.TeamDetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_team_overview.*
import kotlinx.android.synthetic.main.fragment_team_overview.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TeamOverviewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_team_overview, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
    }

    private fun loadData() {
        val teamId = (context as TeamDetailActivity).getTeam().id
        progressBar.visibility = View.VISIBLE
        val callback = RetrofitAdapter.createAPI(BASE_URL).teamOverviewById(teamId)
        callback.enqueue(object : Callback<TeamCallback> {
            override fun onFailure(call: Call<TeamCallback>, t: Throwable) {
                progressBar.visibility = View.GONE
                messageTv.text = getString(R.string.error_generic_message)
                Toast.makeText(context!!, getString(R.string.error_generic_message), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<TeamCallback>, response: Response<TeamCallback>) {
                progressBar.visibility = View.GONE
                mainLayout.visibility = View.VISIBLE
                if (response.isSuccessful) {
                    val teamOverview = response.body()!!.data
                    initializeTeamOverview(teamOverview)
                    //country_name.text = response.body()!!.data.country.data.name

                } else {
                    messageTv.text = getString(R.string.error_generic_message)
                    Toast.makeText(context!!, getString(R.string.error_generic_message), Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    private fun initializeTeamOverview(teamOverview: Team) {
        val country = teamOverview.country.data

        val venue = teamOverview.venue
        if (venue != null) {
            Picasso.get().load(venue.data.image_path).into(venueImage)
            venueName.text = venue.data.name
        }

        val uefaRankingData = teamOverview.uefaranking
        if (uefaRankingData != null) {
            uefaRanking.text = uefaRankingData.data.position.toString()
        } else {
            uefaRankingLayout.visibility = View.GONE
        }

        val coach = teamOverview.coach.data
        Picasso.get().load(coach.image_path).into(coachImage)
        coachName.text = coach.common_name

        if (teamOverview.founded != null && teamOverview.founded != 0) {
            yearLayout.visibility = View.VISIBLE
            yearFounded.text = teamOverview.founded.toString()
        } else {
            yearLayout.visibility = View.VISIBLE
        }

        if (teamOverview.twitter != null) {
            twitter.visibility = View.VISIBLE
            twitterHandle.text = teamOverview.twitter
            twitterHandle.setOnClickListener {
                val twitterName = teamOverview.twitter.replace("@", "")
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/$twitterName")))
            }
        } else {
            twitter.visibility = View.GONE
        }

    }

    fun displayFixture(fixture: Fixture, isNextMatch: Boolean, context: Context, view: View) {
        Log.d("BugTracer", "isNextMatch:$isNextMatch")
        if (isNextMatch) {
            view.nextLayout.visibility = View.VISIBLE
            view.nextMatchLabel.visibility = View.VISIBLE

            view.nextMatchDate.text = fixture.time.starting_at.date
            view.nextMatchTime.text = fixture.time.starting_at.time
            view.nextTeamAName.text = fixture.localTeam.data.name
            view.nextTeamBName.text = fixture.visitorTeam.data.name
            Picasso.get().load(fixture.localTeam.data.logo_path).into(view.nextTeamAImage)
            Picasso.get().load(fixture.visitorTeam.data.logo_path).into(view.nextTeamBImage)
        } else {
            view.prevMatchDate.text = fixture.time.starting_at.date
            view.prevHomeScore.text = fixture.scores.localteam_score.toString()
            view.prevAwayScore.text = fixture.scores.visitorteam_score.toString()
            view.prevTeamAName.text = fixture.localTeam.data.name
            view.prevTeamBName.text = fixture.visitorTeam.data.name
            Picasso.get().load(fixture.localTeam.data.logo_path).into(view.prevTeamAImage)
            Picasso.get().load(fixture.visitorTeam.data.logo_path).into(view.prevTeamBImage)
        }
    }

}