package com.android.baseapp.data.repo

import android.content.Context
import com.android.baseapp.data.local.db.AlarmDao
import com.android.baseapp.domain.model.Alarm
import com.android.baseapp.domain.repo.AlarmRepo
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.android.baseapp.data.local.model.AlarmEntity
import com.android.baseapp.presentation.ui.AlarmHelper
import javax.inject.Inject

class AlarmRepoImpl @Inject constructor(
    private val alarmDao: AlarmDao,
    private val context: Context
) : AlarmRepo {

    override fun getAllAlarms(): LiveData<List<Alarm>> {
        return alarmDao.getAllAlarms().map {
            it.map { entity -> entity.toDomainModel() }
        }
    }

    override suspend fun insert(alarm: Alarm) {
        alarmDao.insert(AlarmEntity.fromDomainModel(alarm))
    }

    override suspend fun setAlarm(alarm: Alarm) {
        AlarmHelper(context).startAlarmManager(alarm)
    }

    override suspend fun delete(alarm: Alarm) {
        alarmDao.delete(AlarmEntity.fromDomainModel(alarm))
    }

    override suspend fun getAlarmById(id: Int): Alarm? {
        return alarmDao.getAlarmById(id)?.toDomainModel()
    }
}