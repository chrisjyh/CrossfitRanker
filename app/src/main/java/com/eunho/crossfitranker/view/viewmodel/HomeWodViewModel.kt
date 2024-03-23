package com.eunho.crossfitranker.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eunho.crossfitranker.common.CURRENTBOX
import com.eunho.crossfitranker.common.ioDispatchers
import com.eunho.crossfitranker.data.WodRecord
import com.eunho.crossfitranker.data.firebase.FirebaseManger
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeWodViewModel: ViewModel(){

    private val firebaseManager by lazy {
        FirebaseManger()
    }
    var wodLiveData = MutableLiveData<List<WodRecord>>()

    fun wodListData(){
        viewModelScope.launch {
            val wodList = withContext(ioDispatchers.coroutineContext){
                firebaseManager.boxAllWod(CURRENTBOX)
            }

            wodLiveData.postValue(wodList)
        }
    }

    fun insetSampleData(){
        Log.e("test","insert in")
        viewModelScope.launch {
            withContext(ioDispatchers.coroutineContext){
                firebaseManager.insertWod()
            }
        }
    }


}