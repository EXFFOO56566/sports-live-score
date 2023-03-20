package com.aroniez.futaa.ui.competitions.leagues

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.aroniez.futaa.api.Status
import com.aroniez.futaa.ui.viewmodels.LeaguesViewModel
import com.aroniez.futaa.utils.InjectorUtils
import com.aroniez.futaa.utils.hideLoadingProgress
import com.aroniez.futaa.utils.showLoadingProgress
import com.aroniez.futaa.utils.showMessageLayout
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*


class Competitions1Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.aroniez.futaa.R.layout.include_recyclerview_progressbar_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadCompetitions()

    }


    private fun loadCompetitions() {
        showLoadingProgress(baseNestedLayout)
        val leaguesFactory = InjectorUtils.provideLeaguesViewModelFactory(context!!)
        val viewModel = ViewModelProviders.of(this, leaguesFactory).get(LeaguesViewModel::class.java)
        viewModel.getCompetitions().observe(viewLifecycleOwner, Observer { leagues ->
            when {
                leagues.status.name.equals(Status.LOADING) -> {
                    showLoadingProgress(baseNestedLayout)
                }
                leagues.status.name.equals(Status.ERROR) -> {
                    showMessageLayout("Something went wrong", baseNestedLayout)
                }
                else -> {
                    hideLoadingProgress(baseNestedLayout)
                    if (leagues.data != null) {
                        val adapter = Competitions1Adapter(leagues.data, context!!)
                        baseRecyclerView.layoutManager = LinearLayoutManager(context)
                        baseRecyclerView.adapter = adapter
                    }
                }
            }
        })
    }


}