package com.example.pomodorotimer.features.timer.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodorotimer.features.timer.domain.TimerUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TimerViewModel(val timerUseCase: TimerUseCase): ViewModel() {

    val timeLiveData = MutableLiveData<Long>()
    val isTimeRunningLiveData = MutableLiveData<Boolean>()
    var isTimerPausedLiveData = MutableLiveData<Boolean>()

    init {
        with(timerUseCase){

            timeFlow.onEach {
                if (it == null) {
                    isTimeRunningLiveData.value = false
                    Log.d("STATUS TIMER 1", "${isTimeRunningLiveData.value}")
                }
                timeLiveData.value = it
            }.launchIn(viewModelScope)

            timeIsPausedFlow.onEach {
                isTimerPausedLiveData.value = it
                Log.d("STATUS TIMER 2", "${isTimerPausedLiveData.value}")
            }.launchIn(viewModelScope)
        }
    }

    fun onStartTimer(time: Long) {
        timerUseCase.runTimer(time)
    }

    fun onPauseTimer() {
        timerUseCase.pauseTimer()
    }

    fun onStopTimer() {
        timerUseCase.stopTimer()
        Log.d("MyTagViewModel", "OnStopTimer")
    }

    fun onResumeTimer() {
        Log.d("MyTag", "timer resumed in vmodel")
        timerUseCase.resumeTimer()

    }

}