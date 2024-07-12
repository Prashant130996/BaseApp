package com.android.baseapp.presentation.ui

import java.time.LocalDateTime

interface DateTimeListener {
    /**
     * Function to send date time to fragment
     * @param dateTime LocalDateTime is used to merge date and time.
     * */
    fun sendDateTime(dateTime: LocalDateTime)
}