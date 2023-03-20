package com.aroniez.futaa.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aroniez.futaa.AppExecutors
import com.aroniez.futaa.R
import com.aroniez.futaa.api.BASE_URL
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.Status
import com.aroniez.futaa.api.callbacks.MatchesCallback
import com.aroniez.futaa.database.SoccerDatabase
import com.aroniez.futaa.ui.viewmodels.FixturesViewModel
import com.aroniez.futaa.utils.*
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_message_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavoriteMatchesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.include_recyclerview_progressbar_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFavorites()
    }

    private fun getFavorites() {
        showLoadingProgress(baseNestedLayout)
        val factory = InjectorUtils.provideFixturesViewModelFactory(context!!)
        val viewModel = ViewModelProviders.of(this, factory).get(FixturesViewModel::class.java)
        viewModel.getFavoritesMatches().observe(viewLifecycleOwner, Observer { leagues ->
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
                        displayFixtures(ArrayList(leagues.data), baseRecyclerView, context!!, false)
                        baseMessageTextView.visibility = View.GONE
                    } else {
                        showMessageLayout("No favorite matches found", baseNestedLayout)
                    }
                }
            }
        })
    }


    fun loadCompetitions() {
        showLoadingProgress(baseNestedLayout)
        AppExecutors().diskIO().execute {
            val favoritesDao = SoccerDatabase.getInstance(context!!).favoritesDao()
            val favoriteMatches = favoritesDao.getFavoriteMatchesIds()
            if (favoriteMatches.isNotEmpty()) {
                var formattedId = ""
                for (id in favoriteMatches) {
                    formattedId = if (formattedId.isEmpty()) {
                        id.toString()
                    } else {
                        "$formattedId,$id"
                    }
                }
                AppExecutors().mainThread().execute {
                    val callback = RetrofitAdapter.createAPI(BASE_URL).particularFixtures(formattedId)
                    callback.enqueue(object : Callback<MatchesCallback> {
                        override fun onFailure(call: Call<MatchesCallback>, t: Throwable) {
                            hideLoadingProgress(baseNestedLayout)
                            showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                        }

                        override fun onResponse(call: Call<MatchesCallback>, response: Response<MatchesCallback>) {
                            hideLoadingProgress(baseNestedLayout)
                            if (response.isSuccessful) {
                                val matches = response.body()!!
                                if (matches.data.isNotEmpty()) {
                                    displayFixtures(matches.data, baseRecyclerView, context!!, false)
                                } else {
                                    showMessageLayout("No favorite matches found", baseNestedLayout)
                                }
                            } else {
                                showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                            }
                        }
                    })
                }
            } else {
                showMessageLayout("No favorite matches found", baseNestedLayout)
            }
        }
    }

}
