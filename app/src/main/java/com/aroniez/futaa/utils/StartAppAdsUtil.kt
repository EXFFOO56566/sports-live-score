package com.aroniez.futaa.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.aroniez.futaa.R
import com.startapp.android.publish.ads.banner.Banner
import com.startapp.android.publish.ads.banner.Mrec
import com.startapp.android.publish.adsCommon.StartAppAd


object StartAppAdsUtil {

    fun showNativeAds(context: Context, linearLayout: LinearLayout) {

        val startAppMrec = Mrec(context)
        val mrecParameters = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT)
        mrecParameters.addRule(RelativeLayout.CENTER_HORIZONTAL)
        mrecParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        linearLayout.removeAllViews()
        linearLayout.addView(startAppMrec, mrecParameters)

        val inflater = LayoutInflater.from(context)
        val adView = inflater.inflate(R.layout.include_startapp_native_layout, null, false) as Mrec
        linearLayout.addView(adView)
//
//        linearLayout.removeAllViews()
//        Log.d("StartAppAdsUtil", "linearLayout.childCount1: "+linearLayout.childCount)
//        Log.d("StartAppAdsUtil", "linearLayout.childCount2: "+linearLayout.childCount)

    }

    fun showBannerAds(context: Context, linearLayout: LinearLayout) {
        val startAppMrec = Banner(context)
        val mrecParameters = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT)
        mrecParameters.addRule(RelativeLayout.CENTER_HORIZONTAL)
        mrecParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        linearLayout.addView(startAppMrec, mrecParameters)

    }

    fun showInterstialAds(context: Context) {
        StartAppAd.enableAutoInterstitial()
        StartAppAd.showAd(context)
    }
}