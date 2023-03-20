package com.aroniez.futaa.ui.matches

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aroniez.futaa.R
import com.aroniez.futaa.api.BASE_URL
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.MatchesCallback
import com.aroniez.futaa.utils.*
import kotlinx.android.synthetic.main.activity_date_matches.*
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.baseNestedLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DateMatchesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_matches)

        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val date = intent.getStringExtra("date")
        toolbarTitle.text = date

        loadMatchesFromServer(date!!)

        loadBannerAds(this, homeBannerLayout)
        loadInterstialAds(this)
    }

    private fun loadMatchesFromServer(date: String) {
        showLoadingProgress(baseNestedLayout)
        val callback = RetrofitAdapter.createAPI(BASE_URL).fixturesForDate(date)
        callback.enqueue(this, object : Callback<MatchesCallback> {
            override fun onFailure(call: Call<MatchesCallback>, t: Throwable) {
                hideLoadingProgress(baseNestedLayout)
                showMessageLayout(getString(R.string.error_generic_message), baseNestedLayout)
            }

            override fun onResponse(call: Call<MatchesCallback>, response: Response<MatchesCallback>) {
                hideLoadingProgress(baseNestedLayout)
                if (response.isSuccessful) {
                    val matches = response.body()!!
                    if (matches.data.isNotEmpty()) {
                        if (baseRecyclerView != null) {
                            //val nonLiveMatches = matches.data.filter { it.time.status != "LIVE" }
                            displayFixtures(matches.data, baseRecyclerView, this@DateMatchesActivity, false)
                        }
                    } else {
                        showMessageLayout("No favorite matches found", baseNestedLayout)
                    }
                } else {
                    showMessageLayout(getString(R.string.error_generic_message), baseNestedLayout)
                }
            }
        })
    }

}
