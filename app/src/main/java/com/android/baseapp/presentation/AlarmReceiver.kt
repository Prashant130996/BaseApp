package com.android.baseapp.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.util.Log
import com.android.baseapp.presentation.ui.ReminderNotificationHelper

//Receiver for Alarm
class AlarmReceiver : BroadcastReceiver() {

    companion object {
        // created for playing alarm sounds
        private var mediaPlayer: MediaPlayer? = null

        fun stopAlarm() = mediaPlayer?.apply {
            stop()
            release()
            mediaPlayer = null
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("Prashant", "onReceive: alarm triggered")
        val msg = intent?.getIntExtra("alarm", 0)
        if (context != null) {

            //Display Notification when alarm is triggered.
            ReminderNotificationHelper().displayNotification(context, msg.toString(), "Reminder")

            if (mediaPlayer == null) {
                val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                //val ringtone = RingtoneManager.getRingtone(context, uri)
                mediaPlayer = MediaPlayer.create(context, uri)
                mediaPlayer?.isLooping = true
                mediaPlayer?.start()
            }
        }
    }
}