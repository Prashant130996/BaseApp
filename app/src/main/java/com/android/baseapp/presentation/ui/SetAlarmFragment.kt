package com.android.baseapp.presentation.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.baseapp.R
import com.android.baseapp.databinding.FragmentSetAlarmBinding
import com.android.baseapp.util.isSdkIntTAndAbove
import com.example.movieturn.ui.viewBinding
import com.example.movieturn.utils.toast
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.ZoneId

@AndroidEntryPoint
class SetAlarmFragment : Fragment(R.layout.fragment_set_alarm) {

    private val binding by viewBinding(FragmentSetAlarmBinding::bind)
    private val viewModel by viewModels<SetAlarmViewModel>()
    private lateinit var alarmAdapter: AlarmAdapter

    private val TAG = SetAlarmFragment::class.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alarmAdapter = AlarmAdapter()
        initViews()
        if (isSdkIntTAndAbove()) handleNotification()
        setClickListeners()
        viewModel.allAlarms.observe(viewLifecycleOwner) { alarms ->
            alarmAdapter.submitList(alarms.sortedByDescending { it.time })
        }
    }

    private fun initViews() = with(binding) {
        alarmRv.apply {
            adapter = alarmAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }

    private fun setClickListeners() = binding.run {
        addAlarmFab.setOnClickListener {
            val dialogFragment = SetAlarmDialogFragment.newInstance(object : DateTimeListener {
                override fun sendDateTime(dateTime: LocalDateTime) {
                    val time = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                    viewModel.setAlarm(time)
                }
            })
            dialogFragment.isCancelable = true
            dialogFragment.show(childFragmentManager, dialogFragment.tag)
            //picker.show(childFragmentManager, "time_picker")
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun handleNotification() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "handleNotification: permission is granted")
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            Snackbar.make(binding.snackView, "Notification Blocked", Snackbar.LENGTH_LONG)
                .setAction("Settings") {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    val uri = Uri.fromParts("package", requireContext().packageName, null)
                    intent.setData(uri)
                    startActivity(intent)
                }.show()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) Log.d(TAG, "perms launcher: notification permission granted")
            else toast("Permission required to show notifications")
        }

}