package com.aroniez.futaa.ui.fixture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aroniez.futaa.R
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.utils.NetworkCheckUtil
import com.aroniez.futaa.utils.displayFixtures
import com.aroniez.futaa.utils.loadMediumBannerAds
import com.aroniez.futaa.utils.showMessageLayout
import kotlinx.android.synthetic.main.include_ads_layout.*
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_message_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*


abstract class MatchesBaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.include_recyclerview_progressbar_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
        baseMessageTextView.setOnClickListener { loadData() }

        loadMediumBannerAds(context!!, advertLayout)
    }

    private fun loadData() {
        if (NetworkCheckUtil.connectedToTheNetwork(context!!)) {
            displayFixtures(getMatches(), baseRecyclerView, context!!, false)
        } else {
            showMessageLayout(context!!.getString(R.string.no_internet_connection_message), baseNestedLayout)
        }
    }

    abstract fun getMatches(): ArrayList<Fixture>

    abstract fun getEmptyGamesMessage(): String
}