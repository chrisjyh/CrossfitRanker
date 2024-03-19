package com.eunho.crossfitranker.repository

import com.eunho.crossfitranker.common.CrossfitRankerApplication
import com.eunho.crossfitranker.data.room.RankerDAO
import com.eunho.crossfitranker.data.room.RankerRoomDatabase

class RoomRepository(
) {
    private val recordDao: RankerDAO
    init {
        val recordDB = RankerRoomDatabase.getInstance(CrossfitRankerApplication.getRankerApplication())
        recordDao = recordDB.generateDAO()
    }

//    suspend fun getAllWod(){
//        recordDao.getLocalWod()
//    }

}