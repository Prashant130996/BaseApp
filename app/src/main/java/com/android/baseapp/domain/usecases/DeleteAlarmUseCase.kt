package com.android.baseapp.domain.usecases

import com.android.baseapp.domain.model.Alarm
import com.android.baseapp.domain.repo.AlarmRepo
import javax.inject.Inject

class DeleteAlarmUseCase @Inject constructor(private val repository: AlarmRepo) {
    suspend operator fun invoke(alarm: Alarm) {
        repository.delete(alarm)
    }
}