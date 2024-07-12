package com.android.baseapp.di

import android.content.Context
import androidx.room.Room
import com.android.baseapp.data.local.db.AlarmDao
import com.android.baseapp.data.local.db.AppDatabase
import com.android.baseapp.data.repo.AlarmRepoImpl
import com.android.baseapp.domain.repo.AlarmRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDb(context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "alarm_db"
    ).build()


    @Provides
    fun provideAlarmDao(appDatabase: AppDatabase): AlarmDao {
        return appDatabase.alarmDao()
    }

    @Provides
    @Singleton
    fun provideAlarmRepository(alarmDao: AlarmDao,context: Context): AlarmRepo {
        return AlarmRepoImpl(alarmDao,context)
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }
}