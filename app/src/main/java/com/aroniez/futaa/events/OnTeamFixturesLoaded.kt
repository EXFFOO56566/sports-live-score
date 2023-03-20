package com.aroniez.futaa.events

import com.aroniez.futaa.models.fixture.Fixture

/**
 * This interface is for the implementation of batch loading of chats
 *
 * onLoadMore method is called whenever user scrolls for more data/chats
 * */
interface OnTeamFixturesLoaded {
    fun onFixtureLoaded(fixture: Fixture, isNextMatch: Boolean)
}