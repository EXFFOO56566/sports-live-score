package com.aroniez.futaa.utils

import android.content.Context
import android.content.Intent
import android.net.Uri


object SharingUtil {
    fun shareApp(context: Context) {
        val appPackageName = context.packageName
        val toShare = "Download Futaa app now \nhttps://play.google.com/store/apps/details?id=$appPackageName"
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, toShare)
        context.startActivity(Intent.createChooser(shareIntent, "Share using ..."))
    }

    fun openAppInPlaystore(context: Context) {
        val appPackageName = context.packageName
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (anfe: android.content.ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
        }

    }
}