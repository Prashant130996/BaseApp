package com.android.baseapp.presentation.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.android.baseapp.R
import com.android.baseapp.presentation.MainActivity
import com.android.baseapp.presentation.StopAlarmBroadcastReceiver
import com.android.baseapp.utils.Constants

/**
 * Helper class to display and dismiss notification.
 * */
class ReminderNotificationHelper {

    fun displayNotification(context: Context, message: String, title: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(notificationManager)

        val builder = NotificationCompat.Builder(context, Constants.CHANNEL_ID).apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(title)
            setContentText("Alarm Triggered")
            setSound(null)
            setStyle(NotificationCompat.BigTextStyle().bigText("Alarm Triggered"))
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            setPriority(NotificationCompat.PRIORITY_MAX)
            setAutoCancel(true)
            setContentIntent(createActivityPendingIntent(context, MainActivity::class.java))
            setDefaults(NotificationCompat.DEFAULT_VIBRATE)
            addAction(
                R.drawable.cross,
                context.getString(R.string.stop_alarm),
                createBroadcastPendingIntent(context, StopAlarmBroadcastReceiver::class.java,)
            )
            priority = NotificationCompat.PRIORITY_HIGH
        }

        val notification = builder.build()
        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL

        notificationManager.notify(1, notification)

    }

    fun dismissNotification(context: Context) {
        (context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager)
            .cancel(1)
    }

    private fun createBroadcastPendingIntent(
        context: Context,
        parameterClass: Class<*>,
    ): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, parameterClass),
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createActivityPendingIntent(
        context: Context,
        parameterClass: Class<*>
    ): PendingIntent {
        val intent =
            Intent(context, parameterClass).apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TOP }
        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            Constants.CHANNEL_ID,
            "Alarm",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Alarm"
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            setShowBadge(true)
            setSound(null, null)
        }
        notificationManager.createNotificationChannel(channel)
    }
}
