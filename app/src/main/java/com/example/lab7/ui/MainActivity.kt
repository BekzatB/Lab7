package com.example.lab7.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lab7.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.navView)
        val navController = Navigation.findNavController(this,
            R.id.navHostFragment
        )
        val appConfiguration = AppBarConfiguration(
            setOf(
                R.id.jobsFragment,
                R.id.savedItemFragment,
                R.id.moreFragment
            )
        )
        setupActionBarWithNavController(navController, appConfiguration)
        navView.setupWithNavController(navController)
    }
}
