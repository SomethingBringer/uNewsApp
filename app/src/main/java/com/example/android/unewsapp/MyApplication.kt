package com.example.android.unewsapp

import android.app.Application
import com.example.android.unewsapp.di.DaggerAppComponent

class MyApplication: Application() {

    val appComponent = DaggerAppComponent.create()
}