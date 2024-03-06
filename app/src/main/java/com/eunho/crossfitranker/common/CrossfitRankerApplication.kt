package com.eunho.crossfitranker.common

import android.app.Application

class CrossfitRankerApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        application = this
    }
    companion object{
        private lateinit var application: Application
        fun getCrossfitRankerApplication() = application
    }
}