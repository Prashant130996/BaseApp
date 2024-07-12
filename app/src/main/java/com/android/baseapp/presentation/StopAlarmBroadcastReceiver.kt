package com.android.baseapp.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.android.baseapp.presentation.ui.ReminderNotificationHelper

class StopAlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        ReminderNotificationHelper().dismissNotification(context!!)
        AlarmReceiver.stopAlarm()
    }
}
