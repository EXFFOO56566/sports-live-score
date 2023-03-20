package com.aroniez.futaa.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aroniez.futaa.R
import com.google.android.gms.ads.MobileAds
import com.startapp.android.publish.adsCommon.StartAppSDK

abstract class BaseAdsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StartAppSDK.init(this@BaseAdsActivity, "204013588", true)
        MobileAds.initialize(this)

    }

}