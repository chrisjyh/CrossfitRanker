package com.eunho.crossfitranker.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eunho.crossfitranker.common.commonToast
import com.eunho.crossfitranker.common.ioDispatchers
import com.eunho.crossfitranker.data.firebase.FirebaseManger
import com.eunho.crossfitranker.data.firebase.WodRankingRecord
import com.eunho.crossfitranker.data.firebase.WodRecordTitle
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeWodViewModel: ViewModel(){

    private val firebaseManager by lazy {
        FirebaseManger()
    }
    var wodLiveData = MutableLiveData<List<WodRecordTitle>>()
    var freeLiveData = MutableLiveData<List<WodRecordTitle>>()
    var rankingRecord = MutableLiveData<List<WodRankingRecord>>()

    /**
     * wod tab 와드 리스트
     *
     * */
    fun wodListData(){
        viewModelScope.launch {
            val wodList = async { firebaseManager.boxAllWod() }
            wodLiveData.postValue(wodList.await())
        }
    }

    /**
     * 와드 상세 보기의 랭킹 표기
     * */
    fun getRecordRanking(wodId:String){
        viewModelScope.launch {
            val wodRanking = firebaseManager.getRecordRanking(wodId)
            rankingRecord.postValue(wodRanking)
        }
    }

    fun searchWods(query: String){
        viewModelScope.launch {

            Log.e("test","searchWods")
            val wodList = firebaseManager.searchWod(query)

            wodLiveData.setValue(wodList)
        }
    }

    /**
     * wod tab 와드 리스트
     *
     * */
    fun freeListData(){
        viewModelScope.launch {
            val wodList = async { firebaseManager.freeAllWod() }
            freeLiveData.postValue(wodList.await())
        }
    }

}