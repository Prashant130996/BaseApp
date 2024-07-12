package com.android.baseapp.domain.usecases

import androidx.lifecycle.LiveData
import com.android.baseapp.domain.model.Alarm
import com.android.baseapp.domain.repo.AlarmRepo
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(private val repository: AlarmRepo) {
    operator fun invoke(): LiveData<List<Alarm>> {
        return repository.getAllAlarms()
    }
}