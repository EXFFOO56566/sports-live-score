package com.aroniez.futaa.utils

import android.content.Context
import android.widget.LinearLayout

private const val facebookAds = "facebook_ads"
private const val googleAds = "google_ads"
private const val startAppAds = "startapp_ads"
private const val randomAds = "random_ads"
private const val selectedAdsToShow = googleAds
private const val showInterstialAds = false

const val adsIntervalInFixtures = 2 //

fun loadBannerAds(context: Context, linearLayout: LinearLayout) {
    when (selectedAdsToShow) {
        googleAds -> AdmobAdsUtil.loadBannerAds(context, linearLayout)
        facebookAds -> FacebookAdsUtil.loadFacebookBannerAd(context, linearLayout)
        startAppAds -> StartAppAdsUtil.showBannerAds(context, linearLayout)
        else -> loadRandomBannerAd(context, linearLayout)
    }
}

fun loadRandomBannerAd(context: Context, linearLayout: LinearLayout) {
    when ((1..3).random()) {
        1 -> AdmobAdsUtil.loadBannerAds(context, linearLayout)
        2 -> FacebookAdsUtil.loadFacebookBannerAd(context, linearLayout)
        else -> AdmobAdsUtil.loadBannerAds(context, linearLayout)
    }
}

fun loadMediumBannerAds(context: Context, linearLayout: LinearLayout) {
    when (selectedAdsToShow) {
        googleAds -> AdmobAdsUtil.loadNativeAds(context, linearLayout)
        facebookAds -> FacebookAdsUtil.loadFacebookNativeAd(context, linearLayout)
        startAppAds -> FacebookAdsUtil.loadFacebookNativeAd(context, linearLayout)
        else -> AdmobAdsUtil.loadNativeAds(context, linearLayout)
    }
}

fun loadRandomMediumBannerAd(context: Context, linearLayout: LinearLayout) {
    when ((1..3).random()) {
        1 -> AdmobAdsUtil.loadNativeAds(context, linearLayout)
        2 -> FacebookAdsUtil.loadFacebookNativeAd(context, linearLayout)
        else -> StartAppAdsUtil.showNativeAds(context, linearLayout)
    }
}

fun loadInterstialAds(context: Context) {
    if (showInterstialAds) {
        when (selectedAdsToShow) {
            googleAds -> AdmobAdsUtil.loadInterstialAds(context)
            facebookAds -> FacebookAdsUtil.loadFacebookInterstialAd(context)
            startAppAds -> StartAppAdsUtil.showInterstialAds(context)
            else -> loadRandomInterstialAd(context)
        }
    }
}

fun loadRandomInterstialAd(context: Context) {
    when ((1..3).random()) {
        1 -> AdmobAdsUtil.loadInterstialAds(context)
        2 -> FacebookAdsUtil.loadFacebookInterstialAd(context)
        else -> StartAppAdsUtil.showInterstialAds(context)
    }
}