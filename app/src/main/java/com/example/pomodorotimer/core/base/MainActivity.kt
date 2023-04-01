package com.example.pomodorotimer.core.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.pomodorotimer.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHost.navController

//        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    //обнуление таймера - ПЕРЕНЕСТИ В ФРАГМЕНТ TIMER SETTINGS

    /*private fun resetTimer() {
        stopTimer()
        time = 0.0
        with(binding) {
            timerScope.text = getTimeStringFromLong(time)
            setHoursEditText.setText("0")
            setMinutesEditText.setText("0")
            setSecondsEditText.setText("0")
        }
    }*/
}