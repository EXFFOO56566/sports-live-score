package com.aroniez.futaa.utils

import android.content.Context
import android.util.Log
import android.widget.LinearLayout
import com.aroniez.futaa.AppExecutors
import com.aroniez.futaa.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback


object AdmobAdsUtil {

    private val TAG = AdmobAdsUtil::class.java.simpleName
    private const val testDeviceId = "22B605704E0092D02E06D0307085A190"

    fun loadRewardedVideoAd(context: Context) {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            context,
            context.getString(R.string.video_reward_unit_id),
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.message)
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Log.d(TAG, "Ad was loaded.")
                    //rewardedAd.show()
                }
            })
    }

    fun loadInterstialAds(context: Context) {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            context.getString(R.string.interstial_unit_id),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.message)
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    //interstitialAd.show()
                }
            })

    }

    fun loadBannerAds(context: Context, linearLayout: LinearLayout) {
        val adThread = object : Thread() {
            override fun run() {
                val request = AdRequest.Builder()
                    //.addTestDevice(testDeviceId)
                    .build()
                AppExecutors().mainThread().execute {
                    val googleAdView = AdView(context)
                    googleAdView.adSize = AdSize.BANNER
                    googleAdView.adUnitId = context.getString(R.string.banner_app_id)
                    googleAdView.loadAd(request)
                    linearLayout.addView(googleAdView)
                }
            }
        }
        adThread.start()
    }

    fun loadNativeAds(context: Context, linearLayout: LinearLayout) {
        val request = AdRequest.Builder()
            // .addTestDevice(testDeviceId)
            .build()

        val googleAdView = AdView(context)
        googleAdView.adSize = AdSize.MEDIUM_RECTANGLE
        googleAdView.adUnitId = context.getString(R.string.banner_app_id)
        googleAdView.loadAd(request)
        linearLayout.removeAllViews()
        linearLayout.addView(googleAdView)
    }
}