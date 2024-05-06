package com.eunho.crossfitranker.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eunho.crossfitranker.common.RoomDataResult
import com.eunho.crossfitranker.common.ioDispatchers
import com.eunho.crossfitranker.data.room.PersonalRecord
import com.eunho.crossfitranker.data.room.Wod
import com.eunho.crossfitranker.repository.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * home > personal 전용 view model
 * */
class HomePersonalViewModel: ViewModel(){

    private val repository: RoomRepository by lazy {
        RoomRepository()
    }

    private var _personalRecord = MutableStateFlow<RoomDataResult<List<PersonalRecord>>>(RoomDataResult.NoConstructor)
    val personalRecordList = _personalRecord.asStateFlow()

    // 개인 와드 리스트
    fun findAllWodList() = viewModelScope.launch {
        withContext(ioDispatchers.coroutineContext) {
            repository.findAllPersonalRecord().collect {
                _personalRecord.value = it
            }
        }
    }
    fun insertRecord(wod: Wod) {
        viewModelScope.launch {
            repository.insertWod(wod)
        }
    }


}