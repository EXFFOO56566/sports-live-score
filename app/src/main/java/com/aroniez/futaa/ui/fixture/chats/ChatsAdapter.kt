package com.aroniez.futaa.ui.fixture.chats

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aroniez.futaa.R
import com.aroniez.futaa.events.OnLoadMoreListener
import com.aroniez.futaa.utils.DateTimeUtil
import com.aroniez.futaa.utils.SharedPreferencesUtil
import kotlinx.android.synthetic.main.incoming_message_chat.view.*
import kotlinx.android.synthetic.main.outgoing_message_chat.view.*

/***
 * This is an abstract base class that is to be extended by both dark and light themed chat adapters
 * */
class ChatsAdapter(recyclerView: RecyclerView, var chats: ArrayList<Chat>, var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val tag = "ChatsAdapter"
    private val inComingChatViewType = 1
    private val outGoingChatViewType = 2
    private val advertViewType = 3
    private val loadMoreViewType = 4

    private var visibleThreshold = 20
    private var lastVisibleItemPosition = 0
    private var firstVisibleItemPosition = 0
    private var previousTotalItemCount = 0
    private var totalItemCount = 0
    private var loading: Boolean = false
    var onLoadMoreListener: OnLoadMoreListener? = null


    init {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy < 0) {
                        totalItemCount = linearLayoutManager.itemCount
                        lastVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                        if (!loading && totalItemCount <= (lastVisibleItemPosition + visibleThreshold)) {
                            Log.d(tag, "End is reached... loading more")
                            // End has been reached
                            if (onLoadMoreListener != null) {
                                onLoadMoreListener!!.onLoadMore()
                            }
                            loading = true
                        }
                    }

                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (recyclerView.canScrollVertically(1)) {
                        //load more...
                    }
                }
            })
        }
    }

    private fun bindIncomingData(chat: Chat, context: Context, itemView: View) {
        itemView.textMessageLayout.visibility = View.VISIBLE

        if (chat.senderUsername!!.startsWith("@")) {
            itemView.incomingMessage.text = chat.senderUsername + ": " + chat.message
        } else {
            itemView.incomingMessage.text = "@" + chat.senderUsername + ": " + chat.message
        }
        itemView.incomingMessageTime.text = DateTimeUtil.getRelativeTime(chat.timestamp)
    }

    private fun bindOutGoingData(chat: Chat, context: Context, itemView: View) {
        itemView.outGoingMessageTime.text = DateTimeUtil.getRelativeTime(chat.timestamp)
        itemView.outGoingMessage.text = chat.message
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            inComingChatViewType -> IncomingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.incoming_message_chat, parent, false))
            outGoingChatViewType -> OutGoingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.outgoing_message_chat, parent, false))
            loadMoreViewType -> LoadMoreViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.include_load_more_layout, parent, false))
            else -> AdvertViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.advert_row_item, parent, false))
        }
    }

    override fun getItemCount() = chats.size

    fun setLoaded() {
        loading = false
    }

    override fun getItemViewType(position: Int): Int {

        val chat = chats[position]
        val currentUserUsername = SharedPreferencesUtil.getUsername(context)
        return when {
            chat.senderUsername == currentUserUsername -> outGoingChatViewType
            chat.message == "Loading_more_texts" -> loadMoreViewType
            position == 10 -> advertViewType
            else -> inComingChatViewType
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = chats[position]
        when (holder) {
            is IncomingViewHolder -> holder.bindData(chat, context)
            is OutGoingViewHolder -> holder.bindData(chat, context)
            else -> {
//                val advertHolder = holder as AdvertViewHolder
//                advertHolder.bindData(context)
            }
        }
    }


    inner class IncomingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(chat: Chat, context: Context) {
            bindIncomingData(chat, context, itemView)
        }
    }

    inner class OutGoingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(chat: Chat, context: Context) {
            bindOutGoingData(chat, context, itemView)
        }
    }

    class LoadMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}