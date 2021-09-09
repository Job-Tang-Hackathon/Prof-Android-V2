package com.gsm.prof_androidv2.utils

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate(){
        super.onCreate()
    }
}