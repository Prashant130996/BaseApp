package com.android.baseapp.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.baseapp.domain.model.Alarm
import com.android.baseapp.domain.usecases.DeleteAlarmUseCase
import com.android.baseapp.domain.usecases.GetAlarmsUseCase
import com.android.baseapp.domain.usecases.InsertAlarmUseCase
import com.android.baseapp.domain.usecases.SetAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetAlarmViewModel @Inject constructor(
    private val insertAlarmUseCase: InsertAlarmUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase,
    getAlarmsUseCase: GetAlarmsUseCase,
    private val setAlarmUseCase: SetAlarmUseCase
) : ViewModel() {


    /**
     * Fun to set alarm data into DB
     * @param dateTime: Time and date for the alarm in milliseconds
     * */
    fun setAlarm(dateTime: Long) {
        viewModelScope.launch {
            val alarm = Alarm(dateTime)
            insertAlarmUseCase.invoke(alarm)
            setAlarmUseCase.invoke(alarm)
        }
    }

    /**
     * Fun to delete alarm data from DB.
     * @param alarm Alarm object to be deleted
     * */
    // TODO: can be called from UI class to delete scheduled alarm.
    fun deleteAlarm(alarm: Alarm) {
        viewModelScope.launch {
            deleteAlarmUseCase.invoke(alarm)
        }
    }

    //Display all the created alarms.
    val allAlarms = getAlarmsUseCase.invoke()

}