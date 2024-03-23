package com.eunho.crossfitranker.view.viewmodel

import android.util.Log
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

class HomePersonalViewModel: ViewModel(){

    private val repository: RoomRepository by lazy {
        RoomRepository()
    }

    private var _personalRecord = MutableStateFlow<RoomDataResult<List<PersonalRecord>>>(RoomDataResult.NoConstructor)
    val personalRecordList get() = _personalRecord.asStateFlow()

    fun findAllWodList() = viewModelScope.launch {
        Log.e("test","in find all wod List")
        withContext(ioDispatchers.coroutineContext) {
            repository.findAllPersonalRecord().collect {
                _personalRecord.value = it
            }
        }
    }
    fun insertRecord(wod: Wod) {
        viewModelScope.launch {
            Log.e("test","insert wod")
            ioDispatchers.launch {
                repository.insertWod(wod)
            }
        }
    }


}