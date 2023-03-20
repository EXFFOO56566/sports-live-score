package com.aroniez.futaa.api

import androidx.lifecycle.LiveData
import com.aroniez.futaa.api.callbacks.*
import com.aroniez.futaa.models.video.VideoHighlight
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

const val fixtures = "fixtures.localTeam,fixtures.visitorTeam,fixtures.league,fixtures.league.country"
const val upcoming = "upcoming.localTeam,upcoming.visitorTeam,upcoming.league,upcoming.league.country"
const val results = "results.localTeam,results.visitorTeam,results.league,results.league.country"
private const val localResults = "localResults.localTeam,localResults.visitorTeam,localResults.league,localResults.league.country"
private const val visitorResults = "visitorResults.localTeam,visitorResults.visitorTeam,visitorResults.league,visitorResults.league.country"
private const val visitorFixtures = "visitorFixtures.localTeam,visitorFixtures.visitorTeam,visitorFixtures.league,visitorFixtures.league.country"
private const val localFixtures = "localFixtures.localTeam,localFixtures.visitorTeam,localFixtures.league,localFixtures.league.country"

interface SoccerEndpoints {

    @GET("standings/season/{id}?api_token=$API_KEY&include=standings.team")
    fun standings(@Path("id") seasonId: Long): Call<StandingsCallback>

    @GET("livescores?api_token=$API_KEY&include=localTeam,visitorTeam")
    fun livescores(): Call<MatchesCallback>

    @GET("livescores/now?api_token=$API_KEY&include=localTeam,visitorTeam,league,league.country")
    fun livescoresNow(): Call<MatchesCallback>

    @GET("leagues?api_token=$API_KEY&include=season.stage,season.round,country:order(code|desc)")
    fun leagues(): Call<LeaguesCallback>

    @GET("leagues?api_token=$API_KEY&include=season.stage,season.round,country:order(code|desc)")
    fun leaguesLiveData(): LiveData<ApiResponse<CompetitionsCallback>>

    @GET("fixtures/multi/{ids}?api_token=$API_KEY&include=localTeam,visitorTeam,league,league.country,round")
    fun fixturesByIdLive(@Path("ids") fixtureIds: String): LiveData<ApiResponse<MatchesCallback>>

    @GET("teams/season/{seasonId}?api_token=$API_KEY")
    fun seasonTeams(@Path("seasonId") seasonId: Long): Call<TeamsCallback>

    @GET
    fun seasonMatches(@Url() url: String): Call<SeasonMatchesCallback>

    @GET("seasons/{id}?api_token=$API_KEY&include=upcoming$limit,$upcoming")
    fun seasonUpcoming(@Path("id") seasonId: Long): Call<SeasonUpcomingCallback>

    @GET("seasons/{id}?api_token=$API_KEY&include=fixtures$limit,$fixtures")
    fun seasonFixtures(@Path("id") seasonId: Long): Call<SeasonFixturesCallback>

    @GET("seasons/{id}?api_token=$API_KEY&include=results$limit,$results")
    fun seasonResults(@Path("id") seasonId: Long): Call<SeasonResultsCallback>

    @GET("squad/season/{seasonId}/team/{teamId}?api_token=$API_KEY&include=fixtures")
    fun seasonSquad(@Path("seasonId") seasonId: Long, @Path("teamId") teamId: Long): Call<SeasonTopScorersCallback>

    @GET("topscorers/season/{seasonId}?api_token=$API_KEY&include=goalscorers$playerLimit:order(goals|desc),goalscorers.player,assistscorers$playerLimit:order(assists|desc),assistscorers.player,cardscorers$playerLimit:order(yellowcards|desc),cardscorers.player,goalscorers.team,cardscorers.team,assistscorers.team")
    fun seasonTopPlayers(@Path("seasonId") seasonId: Long): Call<SeasonTopScorersCallback>

    @GET("leagues/{id}?api_token=$API_KEY")
    fun leagueById(@Path("id") leagueId: Long): Call<LeagueCallback>

    @GET("teams/{id}?api_token=$API_KEY")
    fun teamById(@Path("id") leagueId: Long): Call<TeamCallback>

    @GET("teams/{id}?api_token=$API_KEY&include=visitorResults$limit,$visitorResults,localResults$limit,$localResults")
    fun teamResultsById(@Path("id") leagueId: Long): Call<TeamCallback>

    @GET("teams/{id}?api_token=$API_KEY&include=visitorFixtures$limit,$visitorFixtures,localFixtures$limit,$localFixtures,upcoming$limit,$upcoming")
    fun teamFixturesById(@Path("id") leagueId: Long): Call<TeamCallback>

    @GET("teams/{id}?api_token=$API_KEY&include=squad.player,squad.position")
    fun teamSquadById(@Path("id") leagueId: Long): Call<TeamCallback>

    @GET("teams/{id}?api_token=$API_KEY&include=venue,country,coach,uefaranking")
    fun teamOverviewById(@Path("id") leagueId: Long): Call<TeamCallback>

    @GET("teams/{id}?api_token=$API_KEY&include=transfers.player")
    fun teamTransfersById(@Path("id") leagueId: Long): Call<TeamCallback>

    @GET("countries?api_token=$API_KEY&include=leagues")
    fun countries(): Call<CountriesCallback>

    @GET("fixtures/{id}/?api_token=$API_KEY&include=localTeam,visitorTeam,goals,cards,substitutions,lineup,stats,league,league.country,round")
    fun fixturesById(@Path("id") fixtureId: Long): Call<MatchCallback>

    @GET("fixtures/between/{from}/{to}?api_token=$API_KEY&include=localTeam,visitorTeam,league,league.country")
    fun fixturesBetweenDates(@Path("from") todayDate: String, @Path("to") toDate: String): Call<MatchesCallback>

    @GET("fixtures/date/{date}?api_token=$API_KEY&include=localTeam,visitorTeam,league,league.country,round")
    fun fixturesForDate(@Path("date") todayDate: String): Call<MatchesCallback>

    @GET("fixtures/multi/{ids}?api_token=$API_KEY&include=localTeam,visitorTeam,league,league.country,round")
    fun particularFixtures(@Path("ids") fixtureIds: String): Call<MatchesCallback>

    @GET("commentaries/fixture/{id}?api_token=$API_KEY")
    fun commentary(@Path("id") fixtureId: Long): Call<CommentariesCallback>

    @GET("odds/fixture/{id}/bookmaker/{bookmaker}?api_token=$API_KEY")
    fun fixtureBookmakerOdds(
            @Path("id") fixtureId: Long,
            @Path("bookmaker") bookmaker: Long
    ): Call<OddsCallback>

    @GET("?players/{id}?api_token=$API_KEY")
    fun player(@Path("id") playerId: Long): Call<PlayerCallback>

    @GET("head2head/{team1id}/{team2id}?api_token=$API_KEY&include=localTeam,visitorTeam")
    fun getH2H(@Path("team1id") team1id: Long, @Path("team2id") team2id: Long): Call<MatchesCallback>

    @GET("v1")
    fun videos(): Call<ArrayList<VideoHighlight>>

    @GET("news")
    fun news(): Call<NewsCallback>

    @GET("news/{id}/{slug}")
    fun singleNews(@Path("id") news_id: String, @Path("slug") slug: String): Call<SingleNewsCallback>

}
