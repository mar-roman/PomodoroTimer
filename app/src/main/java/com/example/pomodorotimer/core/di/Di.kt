package com.example.pomodorotimer.core.di

import com.example.pomodorotimer.features.timer.domain.TimerUseCase
import com.example.pomodorotimer.features.timer.presentation.TimerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val myAppModules = module {
    // definition of Presenter

    single { TimerUseCase() }

    viewModel { TimerViewModel(get()) }

}