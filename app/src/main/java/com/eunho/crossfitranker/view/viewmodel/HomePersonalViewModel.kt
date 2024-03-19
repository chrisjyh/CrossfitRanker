package com.eunho.crossfitranker.view.viewmodel

import androidx.lifecycle.ViewModel
import com.eunho.crossfitranker.common.RoomDataResult
import com.eunho.crossfitranker.data.room.PersonalRecordRecycler
import com.eunho.crossfitranker.repository.RoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomePersonalViewModel: ViewModel(){

    private val repository: RoomRepository by lazy {
        RoomRepository()
    }

    private val ioDispatcher = CoroutineScope(Dispatchers.IO)
    private var _stateFlowPersonalList = MutableStateFlow<RoomDataResult<List<PersonalRecordRecycler>>>(RoomDataResult.NoConstructor)
    val PersonalWodResult = _stateFlowPersonalList.asStateFlow()




}