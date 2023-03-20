package com.aroniez.futaa.utils

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.LinearLayout
import com.aroniez.futaa.AppExecutors
import com.aroniez.futaa.R
import com.aroniez.futaa.api.englishLang
import com.aroniez.futaa.api.selectedLang
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.models.fixture.color.TeamColor
import com.aroniez.futaa.models.team.Team
import com.aroniez.futaa.ui.fixture.MatchAdapterBundle
import com.aroniez.futaa.ui.fixture.MatchDetailActivity
import com.aroniez.futaa.ui.teams.TeamDetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.include_fixture_finished.view.*
import kotlinx.android.synthetic.main.include_fixture_layout.view.*
import kotlinx.android.synthetic.main.include_live_game_layout.view.*
import kotlinx.android.synthetic.main.include_match_layout.view.*

object MatchUtil {
    fun initializeMatch(fixture: Fixture, itemView: View, matchAdapterBundle: MatchAdapterBundle) {

        if (fixture.time.status == "FT" || fixture.time.status == "FT_PEN") {
            itemView.matchLayout.visibility = View.GONE
            itemView.liveLayout.visibility = View.GONE
            itemView.finishedLayout.visibility = View.VISIBLE

            itemView.homeScore.text = fixture.scores.localteam_score.toString()
            itemView.awayScore.text = fixture.scores.visitorteam_score.toString()
            itemView.matchDate.text = fixture.time.starting_at.date
        } else {
            itemView.finishedLayout.visibility = View.GONE
            if (fixture.time.status == "NS" || fixture.time.status == "TBA" || fixture.time.status == "AET" || fixture.time.status == "POSTP") {
                itemView.matchLayout.visibility = View.VISIBLE
                itemView.liveLayout.visibility = View.GONE
            } else if (fixture.time.status == "LIVE" || fixture.time.status == "HT") {
                itemView.matchLayout.visibility = View.GONE
                itemView.liveLayout.visibility = View.VISIBLE

                itemView.blinking.visibility = View.VISIBLE
                val anim = AlphaAnimation(0.0f, 1.0f)
                anim.duration = 500
                anim.startOffset = 20
                anim.repeatMode = Animation.REVERSE
                anim.repeatCount = Animation.INFINITE
                itemView.blinking.startAnimation(anim)

                if (fixture.time.status == "HT") {
                    itemView.liveTime.text = "LIVE HT"
                } else {
                    if (fixture.time.injury_time != null && fixture.time.injury_time > 0) {
                        itemView.liveTime.text = "LIVE " + fixture.time.minute + "+" + fixture.time.injury_time
                    } else {
                        itemView.liveTime.text = "LIVE " + fixture.time.minute
                    }
                }
                itemView.teamAScore.text = fixture.scores.localteam_score.toString()
                itemView.teamBScore.text = fixture.scores.visitorteam_score.toString()
            } else {

            }
            itemView.fixtureDate.text = DateTimeUtil.getRelativeDate(fixture.time.starting_at.date, matchAdapterBundle.context)
            if (fixture.time.status == "TBA" || fixture.time.status == "AET" || fixture.time.status == "POSTP") {
                itemView.fixtureTime.text = fixture.time.status
            } else {
                val time = fixture.time.starting_at.time.substring(0, fixture.time.starting_at.time.length - 3)
                //itemView.fixtureTime.text = time
                itemView.fixtureTime.text = DateTimeUtil.getDateCurrentTimeZone(fixture.time.starting_at.timestamp)
            }
        }

        if (matchAdapterBundle.favoriteMatchesIds.contains(fixture.id)) {
            itemView.favoriteFixture.setImageResource(R.drawable.ic_star_grey600_24dp)
        } else {
            //itemView.favoriteFixture.setImageResource(R.drawable.ic_star_outline_grey600_24dp)
        }

//        itemView.favoriteFixture.setOnClickListener {
//            FavoriteUtil.addMatchToFavorites(fixture, matchAdapterBundle, itemView.favoriteFixture)
//        }

        itemView.teamAName.text = fixture.localTeam.data.name
        itemView.teamBName.text = fixture.visitorTeam.data.name


        Picasso.get()
                .load(fixture.localTeam.data.logo_path)
                .placeholder(R.drawable.goals)
                //.resize(36,36)
                .into(itemView.teamAImage)
        Picasso.get()
                .load(fixture.visitorTeam.data.logo_path)
                .placeholder(R.drawable.goals)
                //.resize(36,36)
                .into(itemView.teamBImage)

        if (selectedLang != englishLang) {
            AppExecutors().networkIO().execute {
                val translatedLocalText = TranslatorUtil.translateText(fixture.localTeam.data.name, matchAdapterBundle.translator)
                val translatedVisitText = TranslatorUtil.translateText(fixture.visitorTeam.data.name, matchAdapterBundle.translator)
                AppExecutors().mainThread().execute {
                    itemView.teamAName.text = translatedLocalText
                    itemView.teamBName.text = translatedVisitText
                }
            }
        }

        itemView.fixtureLayout.setOnClickListener { openMatchDetailsActivity(fixture, matchAdapterBundle.context) }

        val homeLayout = itemView.findViewById<LinearLayout>(R.id.homeTeamLayout)
        homeLayout.setOnClickListener {
            if (fixture.colors != null) {
                openTeamDetailsActivity(fixture.localTeam.data, fixture.colors.localteam, matchAdapterBundle)
            } else {
                openTeamDetailsActivity(fixture.localTeam.data, null, matchAdapterBundle)
            }
        }
        itemView.visitingTeamLayout.setOnClickListener {
            if (fixture.colors != null) {
                openTeamDetailsActivity(fixture.visitorTeam.data, fixture.colors.visitorteam, matchAdapterBundle)
            } else {
                openTeamDetailsActivity(fixture.visitorTeam.data, null, matchAdapterBundle)
            }
        }
    }

    private fun openTeamDetailsActivity(team: Team, color: TeamColor?, matchAdapterBundle: MatchAdapterBundle) {
        val intent = Intent(matchAdapterBundle.context, TeamDetailActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.putExtra("team", team)
        intent.putExtra("colors", color)
        if (matchAdapterBundle.shouldOpenTeamDetail) {
            //(matchAdapterBundle.context as TeamDetailActivity).finish()
            matchAdapterBundle.context.startActivity(intent)
        } else {
            matchAdapterBundle.context.startActivity(intent)
        }
    }

    private fun openMatchDetailsActivity(fixture: Fixture, context: Context) {
        val intent = Intent(context, MatchDetailActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.putExtra("match", fixture)
        context.startActivity(intent)
    }
}