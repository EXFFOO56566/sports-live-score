package com.aroniez.futaa.events

/**
 * This interface is for the implementation of batch loading of chats
 *
 * onLoadMore method is called whenever user scrolls for more data/chats
 * */
interface OnLoadMoreListener {
    fun onLoadMore()
}