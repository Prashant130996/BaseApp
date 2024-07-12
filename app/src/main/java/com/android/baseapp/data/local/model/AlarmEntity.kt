// data/local/model/AlarmEntity.kt
package com.android.baseapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.baseapp.domain.model.Alarm

@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val time: Long,
) {
    fun toDomainModel(): Alarm {
        return Alarm(
            time = time,
        )
    }

    companion object {
        fun fromDomainModel(alarm: Alarm): AlarmEntity {
            return AlarmEntity(
                time = alarm.time,
            )
        }
    }
}