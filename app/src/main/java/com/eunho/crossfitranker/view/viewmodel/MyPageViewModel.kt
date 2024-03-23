package com.eunho.crossfitranker.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eunho.crossfitranker.data.WodRecord
import com.eunho.crossfitranker.data.firebase.FirebaseManger

class MyPageViewModel: ViewModel(){
    private val firebaseManager by lazy {
        FirebaseManger()
    }
    var joinWodList = MutableLiveData<List<WodRecord>>()



}