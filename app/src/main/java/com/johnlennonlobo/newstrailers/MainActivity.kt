package com.johnlennonlobo.newstrailers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.johnlennonlobo.newstrailers.di.MainComponent
import com.johnlennonlobo.newstrailers.di.MoviesApplication

class MainActivity : AppCompatActivity() {

    lateinit var mainComponent: MainComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = (applicationContext as MoviesApplication)
            .appComponent
            .mainComponent()
            .create()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}