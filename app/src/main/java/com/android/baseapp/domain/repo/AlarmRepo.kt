package com.android.baseapp.domain.repo

import androidx.lifecycle.LiveData
import com.android.baseapp.domain.model.Alarm

interface AlarmRepo {
    fun getAllAlarms(): LiveData<List<Alarm>>
    suspend fun insert(alarm: Alarm)
    suspend fun setAlarm(alarm: Alarm)
    suspend fun delete(alarm: Alarm)
    suspend fun getAlarmById(id: Int): Alarm?
}