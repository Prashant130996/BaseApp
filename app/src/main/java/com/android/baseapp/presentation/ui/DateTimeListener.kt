package com.android.baseapp.presentation.ui

import java.time.LocalDateTime

interface DateTimeListener {
    fun sendDateTime(dateTime: LocalDateTime)
}