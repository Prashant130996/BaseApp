package com.android.baseapp.presentation.ui

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.android.baseapp.domain.model.Alarm
import com.android.baseapp.presentation.AlarmReceiver

class AlarmHelper(val context: Context) {

    @SuppressLint("ScheduleExactAlarm")
    fun startAlarmManager(alarm: Alarm) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = createPendingIntent(alarm)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarm.time,
            pendingIntent
        )
    }

    @SuppressLint("WrongConstant")
    private fun createPendingIntent(alarm: Alarm): PendingIntent {
        val intent =
            Intent(context, AlarmReceiver::class.java).apply { putExtra("alarm", alarm.time) }
        return PendingIntent.getBroadcast(
            context,
            alarm.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

}
