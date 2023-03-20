package com.aroniez.futaa.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.aroniez.futaa.R
import com.aroniez.futaa.models.fixture.Fixture


object NotificationUtil {

    fun showGoalNotification(context: Context, fixtureId: Fixture, title: String, score: String, intent: Intent) {

        var notifyID = 8520
        var CHANNEL_ID = "futaa_channel"

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        intent.putExtra("id", fixtureId.id)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val goalSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.packageName + "/raw/notification")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager!!.createNotificationChannel(NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_DEFAULT))
        }

        // val smallIcon = getResources().getIdentifier("notification_icon", "id", android.R::class.java.getPackage().name)
        val bigIcon = BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, "futaa_channel")
                .setSmallIcon(R.drawable.goals)
                .setLargeIcon(bigIcon)
                .setContentTitle(title)
                .setContentText(score)
                .setAutoCancel(true)
                .setSound(goalSound)
                .setChannelId(CHANNEL_ID)
                .setContentIntent(pendingIntent) as NotificationCompat.Builder
        val notification = notificationBuilder.build()

        notificationManager!!.notify(fixtureId.id.toInt() /* ID of notification */, notification)
    }
}