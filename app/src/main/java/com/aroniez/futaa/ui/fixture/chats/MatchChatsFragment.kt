package com.aroniez.futaa.ui.fixture.chats

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aroniez.futaa.AppExecutors
import com.aroniez.futaa.R
import com.aroniez.futaa.api.chatsBatchCount
import com.aroniez.futaa.database.SoccerDatabase
import com.aroniez.futaa.events.OnLoadMoreListener
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.ui.fixture.MatchAdapterBundle
import com.aroniez.futaa.ui.fixture.MatchDetailActivity
import com.aroniez.futaa.ui.login.LoginActivity
import com.aroniez.futaa.utils.MatchUtil
import com.aroniez.futaa.utils.NetworkCheckUtil
import com.aroniez.futaa.utils.SharedPreferencesUtil
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chats.composeText
import kotlinx.android.synthetic.main.activity_chats.sendMessage
import kotlinx.android.synthetic.main.fragment_chats.*
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_match_layout.*
import java.util.*
import kotlin.collections.ArrayList


class MatchChatsFragment : Fragment() {

    var mCurrentPage = 1
    var itemPos = 0
    var mLastKey = ""
    var adapter: ChatsAdapter? = null
    var chats: ArrayList<Chat>? = ArrayList()
    var chatsReference: DatabaseReference? = null

    private var currentUsername = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onResume() {
        super.onResume()
        checkIfUserIsLoggedIn()
    }

    private fun checkIfUserIsLoggedIn() {
        if (SharedPreferencesUtil.checkIfUserLoggedInFromPrefs(context!!)) {
            composeText.isEnabled = true
            sendMessage.isEnabled = true
            googleLoginButton.visibility = View.GONE
            composeText.hint = "Type a message"
        } else {
            googleLoginButton.visibility = View.VISIBLE
            composeText.isEnabled = false
            sendMessage.isEnabled = false
            composeText.hint = ""
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // currentUsername = SharedPreferenceUtil.getUsernameFromPrefs(this)
        val match = (context!! as MatchDetailActivity).intent.getSerializableExtra("match") as Fixture
        val matchChatRoomName = match.localTeam.data.name.toLowerCase().replace(" ", "_") + "_vs_" + match.visitorTeam.data.name.toLowerCase().replace(" ", "_")

        chatsReference = FirebaseDatabase.getInstance()
                .getReference("chats")
                .child(matchChatRoomName)

        initializeChats()

        loadInitialMessages()

        currentUsername = SharedPreferencesUtil.getUsername(context!!)

        checkIfUserIsLoggedIn()

        googleLoginButton.setOnClickListener {
            startActivity(Intent(context!!, LoginActivity::class.java))
        }

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                loadLiveMatchesFromDb(match.id)
            }
        }, 0, 1000 * 30)

        sendMessage.setOnClickListener {
            if (NetworkCheckUtil.connectedToTheNetwork(context!!)) {
                val updateRef = chatsReference!!.push()
                val chat = Chat()
                chat.message = composeText.text.toString()
                chat.senderUsername = SharedPreferencesUtil.getUsername(context!!)
                chat.matchTitle = matchChatRoomName
                chat.matchId = match.id
                val postValues = chat.toMap()
                updateRef.setValue(postValues)
                composeText.text.clear()
            } else {
                Toast.makeText(context!!, "Connect to internet to continue", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loadLiveMatchesFromDb(matchId: Long) {
        AppExecutors().diskIO().execute {
            val fragmentContext = context
            if (fragmentContext != null) {
                val dbInstance = SoccerDatabase.getInstance(fragmentContext)
                val fixture = dbInstance.fixtureDao().getLiveMatchById(matchId)
                if (fixture != null) {
                    AppExecutors().mainThread().execute {
                        if (fixtureLayout != null) {
                            val matchAdapterBundle = MatchAdapterBundle(arrayListOf(), fragmentContext, true)
                            MatchUtil.initializeMatch(fixture, fixtureLayout, matchAdapterBundle)
                        }
                    }
                }
            }
        }
    }


    fun initializeChats() {
        val layoutManager = LinearLayoutManager(context!!)
        layoutManager.reverseLayout = true
        baseRecyclerView.layoutManager = layoutManager

        adapter = ChatsAdapter(baseRecyclerView, chats!!, context!!)
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