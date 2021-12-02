package com.d2k.weatherforecasting.ui.home.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.d2k.weatherforecasting.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}