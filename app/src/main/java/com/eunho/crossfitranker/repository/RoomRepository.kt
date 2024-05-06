package com.eunho.crossfitranker.repository

import com.eunho.crossfitranker.common.CrossfitRankerApplication
import com.eunho.crossfitranker.common.RoomDataResult
import com.eunho.crossfitranker.data.room.RankerDAO
import com.eunho.crossfitranker.data.room.RankerRoomDatabase
import com.eunho.crossfitranker.data.room.Wod
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * Local 데이터 repository
 * */
class RoomRepository(
) {
    private val recordDao: RankerDAO
    init {
        val recordDB = RankerRoomDatabase.getInstance(CrossfitRankerApplication.getRankerApplication())
        recordDao = recordDB.generateDAO()
    }
    /**
     * 개인 와드 등록
     * */
    fun insertWod(wod: Wod){
        recordDao.insertWod(wod)
    }

    /**
     * 개인 와드및 기록 리스트
     * */
    fun findAllPersonalRecord() = flow {
        emit(RoomDataResult.Success(recordDao.findAllPersonalRecord()))
    }.catch { exception -> RoomDataResult.Error(exception) }
}