package com.example.pomodorotimer.features.timer.domain

import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class TimerUseCase {

    private var timer = Timer()
    val timeFlow = MutableStateFlow<Long?>(0)
    val timeIsPausedFlow = MutableStateFlow<Boolean>(true)

    fun runTimer(time: Long) {
        if (timeIsPausedFlow.value) {
            timeIsPausedFlow.value = false
            timeFlow.value = time
            timer.scheduleAtFixedRate(TimeTask(), 0, 1000)
        }
    }

    private inner class TimeTask : TimerTask() {
        override fun run() {
            if (timeFlow.value != null) {
                if (isTimerRunning(timeFlow.value!!)) {
                    if (isTimerEnded(timeFlow.value!!)) {
                        timeFlow.value = null
                        timeIsPausedFlow.value = true
                        timer.cancel()
                    }
                    timeFlow.value = timeFlow.value!! - 1
                }
            }
        }
    }

    fun pauseTimer() {
        timer.cancel()
        timeIsPausedFlow.value = true

        makeNewTimer()
    }

    fun resumeTimer() {
        if (timeFlow.value != null) runTimer(timeFlow.value!!)
        timeIsPausedFlow.value = false
    }

    fun stopTimer() {
        timer.cancel()
        timeIsPausedFlow.value = true
        timeFlow.value = 0

        makeNewTimer()
    }

    private fun makeNewTimer() {
        val newTimer = Timer()
        timer = newTimer
    }

    private fun isTimerEnded(time: Long) = time <= 0

    private fun isTimerRunning(time: Long) = time > 0
}