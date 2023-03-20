package com.aroniez.futaa.utils

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.facebook.ads.*
import kotlinx.android.synthetic.main.include_ads_layout.view.*
import kotlinx.android.synthetic.main.include_facebook_native_ad.view.*


object FacebookAdsUtil {
    val TAG = FacebookAdsUtil::class.java.simpleName

    fun loadFacebookInterstialAd(context: Context) {
        val interstitialAd = InterstitialAd(context, facebookInterstialPlacementId)

        interstitialAd.setAdListener(object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad?) {
                Log.d(TAG, "interstitialAd onInterstitialDisplayed ")
            }

            override fun onAdClicked(ad: Ad?) {
                Log.d(TAG, "interstitialAd onAdClicked ")
            }

            override fun onInterstitialDismissed(ad: Ad?) {
                Log.d(TAG, "interstitialAd onInterstitialDismissed ")

            }

            override fun onError(ad: Ad?, error: AdError?) {
                Log.e(TAG, "interstitialAd failed to load: " + error!!.errorMessage)
            }

            override fun onAdLoaded(ad: Ad?) {
                interstitialAd.show()
            }

            override fun onLoggingImpression(ad: Ad?) {
            }

        })

        interstitialAd.loadAd()

    }

    fun loadFacebookBannerAd(context: Context, linearLayout: LinearLayout) {
        val adView = AdView(context, facebookBannerPlacementId, AdSize.BANNER_HEIGHT_50)
        linearLayout.removeAllViews()
        linearLayout.addView(adView)
        adView.setAdListener(object : AdListener {
            override fun onAdClicked(ad: Ad?) {
                Log.d(TAG, "loadFacebookBannerAd: onAdClicked")
            }

            override fun onError(ad: Ad?, error: AdError?) {
                Log.d(TAG, "loadFacebookBannerAd: onError: " + error!!.errorMessage)
            }

            override fun onAdLoaded(ad: Ad?) {
                Log.d(TAG, "loadFacebookBannerAd: onAdLoaded")
            }

            override fun onLoggingImpression(ad: Ad?) {
                Log.d(TAG, "loadFacebookBannerAd: onLoggingImpression")
            }
        })

        adView.loadAd()
    }

    fun loadFacebookNativeAd(context: Context, view: View) {
        val nativeAd = NativeAd(context, facebookNativePlacementId)

        nativeAd.setAdListener(object : NativeAdListener {
            override fun onMediaDownloaded(ad: Ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.")
            }

            override fun onError(ad: Ad, adError: AdError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.errorMessage)
            }

            override fun onAdLoaded(ad: Ad) {
                //ad.loadAd()
                if (nativeAd == null || nativeAd != ad) {
                    Log.d(TAG, "nativeAd == null || nativeAd != ad")
                } else {
                    Log.d(TAG, "inflateAd(nativeAd, context, view)")
                    inflateAd(nativeAd, context, view)

                }
                // Native ad is loaded and ready to be displayed
                Log.d(TAG, "Native ad is loaded and ready to be displayed!")
            }

            override fun onAdClicked(ad: Ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!")
            }

            override fun onLoggingImpression(ad: Ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!")
            }
        })

        // Request an ad
        nativeAd.loadAd()
    }

    private fun inflateAd(nativeAd: NativeAd, context: Context, view: View) {
        nativeAd.unregisterView()
        val inflater = LayoutInflater.from(context)
        val adView = inflater.inflate(com.aroniez.futaa.R.layout.include_facebook_native_ad, view.facebookAdsContainer, false) as LinearLayout
        view.facebookAdsContainer.addView(adView)

        val adOptionsView = AdOptionsView(context, nativeAd, view.facebookAdsContainer)
        view.ad_choices_container.removeAllViews()
        view.ad_choices_container.addView(adOptionsView, 0)

        view.native_ad_title.text = nativeAd.advertiserName
        view.native_ad_body.text = nativeAd.adBodyText
        view.native_ad_social_context.text = nativeAd.adSocialContext
        view.native_ad_call_to_action.visibility = if (nativeAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE
        view.native_ad_call_to_action.text = nativeAd.adCallToAction
        view.native_ad_sponsored_label.text = nativeAd.sponsoredTranslation

        val clickableViews = ArrayList<View>()
        clickableViews.add(view.native_ad_title)
        clickableViews.add(view.native_ad_call_to_action)

        nativeAd.registerViewForInteraction(adView, view.native_ad_media, view.native_ad_icon, clickableViews)
    }
}