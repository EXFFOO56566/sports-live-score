package com.aroniez.futaa.ui.teams.transfers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aroniez.futaa.R
import com.aroniez.futaa.api.BASE_URL
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.TeamCallback
import com.aroniez.futaa.ui.teams.TeamDetailActivity
import com.aroniez.futaa.utils.enqueue
import com.aroniez.futaa.utils.hideLoadingProgress
import com.aroniez.futaa.utils.showLoadingProgress
import com.aroniez.futaa.utils.showMessageLayout
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TeamTransfersFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.include_recyclerview_progressbar_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }


    private fun loadData() {
        showLoadingProgress(baseNestedLayout)
        val callback = RetrofitAdapter.createAPI(BASE_URL).teamTransfersById((context as TeamDetailActivity).getTeam().id)
        callback.enqueue(viewLifecycleOwner, object : Callback<TeamCallback> {
            override fun onFailure(call: Call<TeamCallback>, t: Throwable) {
                showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
            }

            override fun onResponse(call: Call<TeamCallback>, response: Response<TeamCallback>) {
                hideLoadingProgress(baseNestedLayout)
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val transfers = response.body()!!.data.transfers
                        if (transfers != null) {
                            if (transfers.data.size > 0) {
                                val adapter = TeamTransfersAdapter(transfers.data, context!!)
                                baseRecyclerView.layoutManager = LinearLayoutManager(context!!)
                                baseRecyclerView.adapter = adapter
                            } else {
                                showMessageLayout("Team transfers not available now", baseNestedLayout)
                            }
                        } else {
                            showMessageLayout("Team transfers not available now", baseNestedLayout)
                        }
                    } else {
                        showMessageLayout("Team transfers not available now", baseNestedLayout)
                    }
                } else {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                }
            }
        })
    }

}
