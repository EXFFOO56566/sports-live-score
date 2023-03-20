package com.aroniez.futaa

import android.app.Application
import com.facebook.ads.AudienceNetworkAds

class SoccerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AudienceNetworkAds.initialize(this)
        AudienceNetworkAds.isInAdsProcess(this)
    }
}