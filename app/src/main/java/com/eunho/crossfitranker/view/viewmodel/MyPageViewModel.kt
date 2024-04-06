package com.eunho.crossfitranker.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eunho.crossfitranker.common.ioDispatchers
import com.eunho.crossfitranker.data.firebase.FirebaseManger
import com.eunho.crossfitranker.data.firebase.WodRecordTitle
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyPageViewModel: ViewModel(){
    private val firebaseManager by lazy {
        FirebaseManger()
    }
    var joinWodList = MutableLiveData<List<WodRecordTitle>>()

    fun personalWodListData(){
        viewModelScope.launch {
            val wodList = withContext(ioDispatchers.coroutineContext){
                firebaseManager.joinedAllWod()
            }
            joinWodList.postValue(wodList)
        }
    }


}