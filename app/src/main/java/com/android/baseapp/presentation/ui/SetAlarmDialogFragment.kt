package com.android.baseapp.presentation.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.android.baseapp.R
import com.android.baseapp.databinding.DFragmentDateTimeBinding
import com.example.movieturn.utils.toast
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CompositeDateValidator
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class SetAlarmDialogFragment : DialogFragment() {

    private var _binding: DFragmentDateTimeBinding? = null
    private val binding get() = _binding!!
    private lateinit var timePicker: MaterialTimePicker
    private var localDate = LocalDateTime.now()

    companion object {
        private var dateTimeListener: DateTimeListener? = null
        fun newInstance(dateTimeListener: DateTimeListener): SetAlarmDialogFragment {
            val dialog = SetAlarmDialogFragment()
            this.dateTimeListener = dateTimeListener
            return dialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DFragmentDateTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
        timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setTitleText(R.string.alarm_title)
            .setHour(LocalDateTime.now().hour)
            .setMinute(LocalDateTime.now().minute)
            .build()

        timePicker.addOnPositiveButtonClickListener {
            binding.alarmTimeEt.setText("${timePicker.hour}:${timePicker.minute}")
            localDate =
                localDate.withHour(timePicker.hour).withMinute(timePicker.minute).withSecond(0)
        }

    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(true)
            dialog.window?.apply {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                setGravity(Gravity.CENTER_HORIZONTAL)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }


    private fun setClickListeners() = binding.run {

        alarmDateEt.setOnClickListener {
            val constraintsBuilder = CalendarConstraints.Builder()
            val dateValidator = listOf(DateValidatorPointForward.now())
            constraintsBuilder.setValidator(CompositeDateValidator.allOf(dateValidator))
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .setTitleText(R.string.alarm_date)
                .build()
            datePicker.addOnPositiveButtonClickListener { selectedDateInMillis ->
                localDate =
                    Instant.ofEpochMilli(selectedDateInMillis).atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                val date = localDate.format(formatter)
                alarmDateEt.setText(date)
            }
            datePicker.show(parentFragmentManager, "datePicker")
        }

        alarmTimeEt.setOnClickListener {
            timePicker.show(childFragmentManager, "time_picker")
        }

        setAlarmBtn.setOnClickListener {
            when {
                alarmDateEt.text.isNullOrEmpty() -> toast("Please select alarm date")
                alarmTimeEt.text.isNullOrEmpty() -> toast("Please select alarm time")
                (localDate.isBefore(LocalDateTime.now())) -> toast("Please select future time")
                else -> {
                    dateTimeListener?.sendDateTime(localDate)
                    dismiss()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}