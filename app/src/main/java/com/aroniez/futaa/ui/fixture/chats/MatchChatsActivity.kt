package com.aroniez.futaa.ui.fixture.chats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aroniez.futaa.AppExecutors
import com.aroniez.futaa.api.BASE_URL
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.TeamCallback
import com.aroniez.futaa.api.chatsBatchCount
import com.aroniez.futaa.database.SoccerDatabase
import com.aroniez.futaa.events.OnLoadMoreListener
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.ui.fixture.MatchAdapterBundle
import com.aroniez.futaa.utils.MatchUtil
import com.aroniez.futaa.utils.SharedPreferencesUtil
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chats.*
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_match_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MatchChatsActivity : AppCompatActivity() {

    var mCurrentPage = 1
    var itemPos = 0
    var mLastKey = ""
    var adapter: ChatsAdapter? = null
    var chats: ArrayList<Chat>? = ArrayList()
    var chatsReference: DatabaseReference? = null

    private var currentUsername = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.aroniez.futaa.R.layout.activity_chats)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // currentUsername = SharedPreferenceUtil.getUsernameFromPrefs(this)
        val match = intent.getSerializableExtra("match") as Fixture
        val matchAdapterBundle = MatchAdapterBundle(arrayListOf(), this, true)
        MatchUtil.initializeMatch(match, chatLayout, matchAdapterBundle)

        currentUsername = SharedPreferencesUtil.getUsername(this)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                loadLiveMatchesFromDb(match.id)
            }
        }, 0, 1000 * 30)

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


    private fun loadCompetitions(teamId: Long, match: Fixture) {
        // showLoadingProgress()
        val callback = RetrofitAdapter.createAPI(BASE_URL).teamById(teamId)
        callback.enqueue(object : Callback<TeamCallback> {
            override fun onFailure(call: Call<TeamCallback>, t: Throwable) {
                // hideLoadingProgress()
                // showMessageLayout("Something went wrong")
            }

            override fun onResponse(call: Call<TeamCallback>, response: Response<TeamCallback>) {
                //hideLoadingProgress()
                if (response.isSuccessful) {
                    val team = response.body()!!.data
                    //show team
                    val matchChatRoomName = "sdfsdf_vs_sdvds"
                    //val matchChatRoomName = team.name.toLowerCase().replace(" ", "_") + "_vs_" + match.match_awayteam_name.toLowerCase().replace(" ", "_")

                    chatsReference = FirebaseDatabase.getInstance()
                            .getReference("chats")
                            .child(matchChatRoomName)

                    initializeChats()

                    loadInitialMessages()

                    sendMessage.setOnClickListener {
                        val updateRef = chatsReference!!.push()
                        val chat = Chat()
                        chat.message = composeText.text.toString()
                        chat.senderUsername = SharedPreferencesUtil.getUsername(this@MatchChatsActivity)
                        chat.matchTitle = matchChatRoomName
                        chat.matchId = match.id
                        val postValues = chat.toMap()
                        updateRef.updateChildren(postValues)
                        composeText.text.clear()
                    }
                } else {
                    //showMessageLayout("Something went wrong")
                }
            }
        })

    }

    fun initializeChats() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        baseRecyclerView.layoutManager = layoutManager

        adapter = ChatsAdapter(baseRecyclerView, chats!!, this)
        baseRecyclerView.adapter = adapter
        baseRecyclerView.scrollToPosition(0)

        adapter!!.onLoadMoreListener = (object : OnLoadMoreListener {
            override fun onLoadMore() {
                //add progress item
                val loadingChat = Chat()
                loadingChat.message = "Loading_more_texts"
                chats!!.add(loadingChat)

                baseRecyclerView.post {
                    adapter!!.notifyItemInserted(chats!!.size - 1)
                }

                itemPos = 0
                mCurrentPage++

                if (chats!!.size > chatsBatchCount) {
                    loadMoreChats()
                }
            }
        })
    }

    private fun loadInitialMessages() {
        val messageQuery = chatsReference!!.limitToLast(chatsBatchCount)
        messageQuery.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot) {}
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                val chat = dataSnapshot.getValue(Chat::class.java)

                itemPos++

                val mMessageKey = dataSnapshot.key
                if (itemPos == 1) {
                    mLastKey = mMessageKey!!
                }
                if (!chats!!.contains(chat!!)) {
                    chats!!.add(0, chat)
                    adapter!!.notifyItemInserted(0)
                }
                if (baseRecyclerView != null) {
                    baseRecyclerView.scrollToPosition(0)
                }
            }
        })
    }

    private fun loadMoreChats() {
        val messageQuery = chatsReference!!.orderByKey().endAt(mLastKey).limitToLast(chatsBatchCount)
        messageQuery.addChildEventListener(object : ChildEventListener {

            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot) {}
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                val chat = dataSnapshot.getValue(Chat::class.java)

                itemPos++

                if (!chats!!.contains(chat!!)) {
                    chats!!.add(chats!!.size - 1, chat)
                    adapter!!.notifyItemInserted(chats!!.size - 1)
                }

                val messageKey = dataSnapshot.key
                if (itemPos == 1) {
                    val loadingChat = chats!![chats!!.size - 1]
                    if (loadingChat.message == "Loading_more_texts") {
                        chats!!.removeAt(chats!!.size - 1)
                        adapter!!.notifyItemRemoved(chats!!.size - 1)
                    }
                    mLastKey = messageKey!!
                }

                adapter!!.setLoaded()
            }
        })
    }


}