package com.android.baseapp.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.baseapp.databinding.ItemAlarmBinding
import com.android.baseapp.domain.model.Alarm
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class AlarmAdapter : ListAdapter<Alarm, AlarmAdapter.AlarmViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val binding = ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlarmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AlarmViewHolder(private val binding: ItemAlarmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(alarm: Alarm) {

            val instant = Instant.ofEpochMilli(alarm.time)
            val dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()

            binding.alarmTime.text = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy \nhh:mm a"))
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Alarm>() {
            override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
                return oldItem.time == newItem.time
            }

            override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
                return oldItem == newItem
            }
        }
    }
}