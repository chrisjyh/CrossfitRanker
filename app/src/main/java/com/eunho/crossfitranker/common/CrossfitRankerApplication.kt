package com.eunho.crossfitranker.common

import android.app.Application
import com.eunho.crossfitranker.data.room.RankerRoomDatabase

/**
 * 가장 먼저 실행됨
 * */
class CrossfitRankerApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        application = this
        RankerRoomDatabase.getInstance(this)
    }
    companion object{
        private lateinit var application: Application
        fun getRankerApplication() = application
    }
}