package com.aroniez.futaa.ui.fixture.predictions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aroniez.futaa.R
import com.aroniez.futaa.api.BASE_URL
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.OddsCallback
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.ui.fixture.MatchDetailActivity
import com.aroniez.futaa.utils.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_predictions.*
import kotlinx.android.synthetic.main.include_ads_layout.*
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.math.roundToInt

class MatchPredictionsFragment : Fragment() {
    var votesReference: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_predictions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadBannerAds(requireContext(), advertLayout)

        val match = (context as MatchDetailActivity).getMatchObject()
        if (match != null) {
            val localTeamName = match.localTeam.data.name
                .lowercase(Locale.getDefault())
                .replace(" ", "_")
                .replace("#", "_")
                .replace("$", "_")
                .replace("[", "_")
                .replace("]", "_")
            val visitorTeamName = match.visitorTeam.data.name
                .lowercase(Locale.getDefault())
                .replace(" ", "_")
                .replace("#", "_")
                .replace("$", "_")
                .replace("[", "_")
                .replace("]", "_")
            val matchChatRoomName = localTeamName + "_vs_" + visitorTeamName

            votesReference = FirebaseDatabase.getInstance()
                .getReference("votes")
                .child(matchChatRoomName)

            teamAName.text = match.localTeam.data.name
            teamBName.text = match.visitorTeam.data.name

            loadVotes()
            teamAPrediction.setOnClickListener { vote("home", match) }
            drawPrediction.setOnClickListener { vote("draw", match) }
            teamBPrediction.setOnClickListener { vote("away", match) }
            loadFixtureOdds(match)
        }
    }

    private fun loadFixtureOdds(fixture: Fixture) {
        val oddsToShow = arrayOf(1L, 63L, 976105)
        showLoadingProgress(oddsLayout)
        val callback = RetrofitAdapter.createAPI(BASE_URL).fixtureBookmakerOdds(fixture.id, 2)
        callback.enqueue(viewLifecycleOwner, object : Callback<OddsCallback> {
            override fun onFailure(call: Call<OddsCallback>, t: Throwable) {
                Log.d("BugTracer", "error: $t")
                showMessageLayout(context!!.getString(R.string.error_generic_message), oddsLayout)
            }

            override fun onResponse(call: Call<OddsCallback>, response: Response<OddsCallback>) {
                if (oddsLayout != null) {
                    hideLoadingProgress(oddsLayout)
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()!!.data.isNotEmpty()) {
                                val odds =
                                    response.body()!!.data.filter { oddsToShow.contains(it.id) }
                                if (odds.isNotEmpty()) {
                                    val adapter = OddsAdapter(odds)
                                    baseRecyclerView.layoutManager = LinearLayoutManager(context)
                                    baseRecyclerView.adapter = adapter
                                    baseRecyclerView.isNestedScrollingEnabled = true
                                } else {
                                    showMessageLayout("No odds at the moment", oddsLayout)
                                }

                            } else {
                                showMessageLayout("No odds at the moment", oddsLayout)
                            }
                        } else {
                            showMessageLayout(
                                context!!.getString(R.string.error_generic_message),
                                oddsLayout
                            )
                        }

                    } else {
                        showMessageLayout(
                            context!!.getString(R.string.error_generic_message),
                            oddsLayout
                        )
                    }
                }
            }
        })
    }

    private fun vote(prediction: String, match: Fixture) {
        val matchChatRoomName = match.localTeam.data.name
            .toLowerCase(Locale.getDefault()).replace(" ", "_") +
                "_vs_" + match.visitorTeam.data.name
            .toLowerCase(Locale.getDefault()).replace(" ", "_")

        val username = SharedPreferencesUtil.getUsername(requireContext())
        if (NetworkCheckUtil.connectedToTheNetwork(requireContext())) {
            val updateRef = votesReference!!.child(prediction).child(username)
            val vote = Vote()
            vote.vote = prediction
            vote.senderUsername = username
            vote.matchTitle = matchChatRoomName
            vote.matchId = match.id
            val postValues = vote.toMap()
            updateRef.setValue(postValues)

            loadVotes()
        } else {
            Toast.makeText(requireContext(), "Connect to internet to continue", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun loadVotes() {
        val username = SharedPreferencesUtil.getUsername(requireContext())
        votesReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val homeWinVotes = dataSnapshot.child("home").children.count()
                    val drawVotes = dataSnapshot.child("draw").children.count()
                    val awayWinVotes = dataSnapshot.child("away").children.count()

                    val userHasVoted = (
                            dataSnapshot.child("home").child(username).exists() ||
                                    dataSnapshot.child("draw").child(username).exists() ||
                                    dataSnapshot.child("away").child(username).exists())

                    val totalVotesCast = homeWinVotes + drawVotes + awayWinVotes

                    totalVotes.text = "Total votes: $totalVotesCast"
                    Log.d(
                        "BugTracer",
                        "homeWinVotes:$homeWinVotes drawVotes:$drawVotes awayWinVotes:$awayWinVotes totalVotesCast:$totalVotesCast"
                    )
                    Log.d(
                        "BugTracer",
                        "homePercentage:${(((homeWinVotes * 100.0) / totalVotesCast).roundToInt())}"
                    )
                    if (userHasVoted) {
                        voteResultsLayout.visibility = View.VISIBLE
                        votingLayout.visibility = View.GONE

                        teamAVotes.text =
                            if (totalVotesCast == 0) "0%" else (((homeWinVotes * 100.0) / totalVotesCast).roundToInt()).toString() + "%"
                        teamsDrawVotes.text =
                            if (totalVotesCast == 0) "0%" else (((drawVotes * 100.0) / totalVotesCast).roundToInt()).toString() + "%"
                        awayVotes.text =
                            if (totalVotesCast == 0) "0%" else (((awayWinVotes * 100.0) / totalVotesCast).roundToInt()).toString() + "%"

                        if (homeVoteProgress != null) {
                            homeVoteProgress.viewTreeObserver.addOnGlobalLayoutListener {
                                val fullWidth = homeVoteProgress.width
                                val progressWidth =
                                    if (totalVotesCast != 0) (homeWinVotes * fullWidth) / totalVotesCast else 0
                                homeView.layoutParams.width = progressWidth
                                homeView.requestLayout()
                            }
                            drawVoteProgress.viewTreeObserver.addOnGlobalLayoutListener {
                                val fullWidth = drawVoteProgress.width
                                val progressWidth =
                                    if (totalVotesCast != 0) (drawVotes * fullWidth) / totalVotesCast else 0
                                drawView.layoutParams.width = progressWidth
                                drawView.requestLayout()
                            }
                            awayVoteProgress.viewTreeObserver.addOnGlobalLayoutListener {
                                val fullWidth = awayVoteProgress.width
                                val progressWidth =
                                    if (totalVotesCast != 0) (awayWinVotes * fullWidth) / totalVotesCast else 0
                                awayView.layoutParams.width = progressWidth
                                awayView.requestLayout()
                            }
                        }

                    } else {
                        voteResultsLayout.visibility = View.GONE
                        votingLayout.visibility = View.VISIBLE
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {}
        })
    }
}