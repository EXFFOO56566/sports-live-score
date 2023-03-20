package com.aroniez.futaa.ui.fixture.chats

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.aroniez.futaa.utils.AdmobAdsUtil
import kotlinx.android.synthetic.main.include_ads_layout.view.*

//Base class for adverts like banners.
class AdvertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(context: Context) {
        AdmobAdsUtil.loadBannerAds(context, itemView.advertLayout)
    }
}