package com.example.pomodorotimer.features.timer.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pomodorotimer.R
//import com.example.pomodorotimer.features.timer.TimerService
import com.example.pomodorotimer.databinding.FragmentTimerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates


class TimerFragment : Fragment() {

    private lateinit var binding: FragmentTimerBinding
    private var time: Long = 0
    private lateinit var serviceIntent: Intent
    private var isTimerPaused by Delegates.notNull<Boolean>()
    private var stopOrResumeButton = R.string.stop

    private val timerViewModel: TimerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        serviceIntent = Intent(requireActivity().applicationContext, TimerService::class.java)

        time = requireArguments().getLong(ARG_TIME)
        Log.d("MyTag", "time is oncreate = $time")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        Log.d("MyLayoutTag", "onCreateView done successfully!!")
        binding = FragmentTimerBinding.inflate(inflater, container, false)

        if (savedInstanceState == null) startTimer()
        else {
            stopOrResumeButton = savedInstanceState.getInt(ARG_STATE)
            binding.stopButton.text = getText(stopOrResumeButton)
        }

        timerViewModel.timeLiveData.observe(viewLifecycleOwner) {
            binding.timerScope.text = getTimeStringFromLong(it)
        }

        timerViewModel.isTimeRunningLiveData.observe(viewLifecycleOwner) {
            if (!it) findNavController().popBackStack()
        }


        timerViewModel.isTimerPausedLiveData.observe(viewLifecycleOwner) {
            isTimerPaused = it ?: false
        }

        binding.stopButton.setOnClickListener { onStopOnResumeButtonPressed(isTimerPaused) }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            timerViewModel.onStopTimer()
            findNavController().navigateUp()
        }


        //подписка на броадкаст
        /*registerReceiver(
            requireActivity(),
            updateTime,
            IntentFilter(TimerService.TIMER_UPDATED),
            RECEIVER_EXPORTED
        )*/

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ARG_STATE, stopOrResumeButton)
    }

    private fun onStopOnResumeButtonPressed(isTimerPaused: Boolean) {
        if (isTimerPaused) onResumeButtonPressed()
        else onStopButtonPressed()
    }

    private fun onResumeButtonPressed() {
        timerViewModel.onResumeTimer()

        stopOrResumeButton = R.string.stop

        setButtonName(stopOrResumeButton)
    }

    private fun onStopButtonPressed() {
        timerViewModel.onPauseTimer()

        stopOrResumeButton = R.string.resume

        setButtonName(stopOrResumeButton)
    }

    private fun startTimer() {
/*        if (!isMyServiceRunning(TimerService::class.java)) {
            serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
            requireActivity().startService(serviceIntent)
        }*/
        timerViewModel.onStartTimer(time)
    }

    private fun setButtonName(name: Int) {
        binding.stopButton.text = getText(name)
    }


/*    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager =
            requireActivity().application.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }*/


/*    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

*//*            time = intent!!.getLongExtra(TimerService.TIME_EXTRA, 0)
            binding.timerScope.text = getTimeStringFromLong(time)*//*

        }
    }*/


    /*   private fun stopTimer() {
           requireActivity().stopService(serviceIntent)
           binding.statusTimer.text = getText(R.string.timer_is_stop)
       }*/

    private fun getTimeStringFromLong(time: Long): String {
        val roundedTime = time

        val hours = roundedTime % 86400 / 3600
        val minutes = roundedTime % 86400 % 3600 / 60
        val seconds = roundedTime % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Long, min: Long, sec: Long): String =
        String.format("%02d:%02d:%02d", hour, min, sec)

    companion object {
        const val ARG_TIME = "ARG_TIME"
        const val ARG_STATE = "ARG_STATE"
    }

}