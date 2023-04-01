/*
package com.example.pomodorotimer.features.timer

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.pomodorotimer.features.timer.domain.TimerUseCase
import org.koin.android.ext.android.inject
import java.util.*

class TimerService: Service() {

    private val timer = Timer()

    private val repository by inject<TimerUseCase>()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        Log.d("ServiceLog", "OnStartCommand")
        val time = intent.getLongExtra(TIME_EXTRA, 0)
        Log.d("ServiceLog", "time in service = $time")

        repository.timeFlow.value = time

        timer.scheduleAtFixedRate(TimeTask(time), 0, 1000)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    private inner class TimeTask(private var time: Long) : TimerTask() {
        override fun run() {
//            val intent = Intent(TIMER_UPDATED)
            if (isTimerRunning(time)) {
                if (isTimerEnded(time)) stopSelf()
                repository.timeFlow.value = repository.timeFlow.value?.minus(1)
            }
//            intent.putExtra(TIME_EXTRA, time)
//            sendBroadcast(intent)
        }
    }

    private fun isTimerEnded (time: Long) = time <= 0.0

    private fun isTimerRunning(time: Long) = time > 0.0

    companion object {
        const val TIME_EXTRA = "TIME_EXTRA"
        const val TIMER_UPDATED = "TIMER_UPDATED"
    }
}*/
