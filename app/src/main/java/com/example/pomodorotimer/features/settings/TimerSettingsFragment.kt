package com.example.pomodorotimer.features.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pomodorotimer.R
import com.example.pomodorotimer.databinding.FragmentTimerSettingsBinding
import com.example.pomodorotimer.features.timer.presentation.TimerFragment

class TimerSettingsFragment: Fragment(R.layout.fragment_timer_settings) {

    private lateinit var binding: FragmentTimerSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimerSettingsBinding.bind(view)

        binding.startButton.setOnClickListener { onStartButtonPressed() }
    }

    private fun onStartButtonPressed() {
        val time: Long = setTimeLongFromString()
        Log.d("MyTag", "time = $time")

        findNavController().navigate(
            R.id.action_timerSettingsFragment_to_timerFragment,
            bundleOf(TimerFragment.ARG_TIME to time)
        )
    }

    private fun setTimeLongFromString(): Long {

        val restoredHours = binding.setHoursEditText.text.toString().toLong() * 3600
        val restoredMinutes = binding.setMinutesEditText.text.toString().toLong() * 60
        val restoredSeconds = binding.setSecondsEditText.text.toString().toLong()

        return restoredHours + restoredMinutes + restoredSeconds
    }

}