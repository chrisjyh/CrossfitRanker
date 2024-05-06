package com.eunho.crossfitranker.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eunho.crossfitranker.data.firebase.FirebaseManger
import com.eunho.crossfitranker.data.firebase.WodRankingRecord
import com.eunho.crossfitranker.data.firebase.WodRecordTitle
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * home > wod,free 탭 뷰모델
 * */
class HomeWodViewModel: ViewModel(){

    private val firebaseManager by lazy {
        FirebaseManger()
    }
    // wod tab 와드 리스트
    private var _wodLiveData = MutableLiveData<List<WodRecordTitle>>()
    // free tab 와드 리스트
    private var _freeLiveData = MutableLiveData<List<WodRecordTitle>>()
    //
    private var _rankingRecord = MutableLiveData<List<WodRankingRecord>>()

    val wodLiveData = _wodLiveData
    val freeLiveData = _freeLiveData
    val rankingRecord = _rankingRecord

    /**
     * wod tab 와드 리스트
     * */
    fun wodListData(){
        viewModelScope.launch {
            val wodList = async {
                firebaseManager.boxAllWod()
            }
            _wodLiveData.postValue(wodList.await())
        }
    }

    /**
     * 와드 상세 보기의 랭킹 표기
     * */
    fun getRecordRanking(wodId:String){
        viewModelScope.launch {
            _rankingRecord.postValue(firebaseManager.getRecordRanking(wodId))
        }
    }

    /**
     * 와드 검색
     * */
    fun searchWods(query: String){
        viewModelScope.launch {
            _wodLiveData.setValue( firebaseManager.searchWod(query))
        }
    }

    /**
     * wod tab 와드 리스트
     * */
    fun freeListData(){
        viewModelScope.launch {
            val wodList = async { firebaseManager.freeAllWod() }
            _freeLiveData.postValue(wodList.await())
        }
    }

}