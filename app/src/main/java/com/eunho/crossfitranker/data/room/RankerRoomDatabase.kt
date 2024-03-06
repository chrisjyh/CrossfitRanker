package com.eunho.crossfitranker.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Wod::class,
        Record::class
    ],
    version = 1
)
abstract class RankerRoomDatabase : RoomDatabase(){
    abstract fun generateDAO(): RankerDAO
}