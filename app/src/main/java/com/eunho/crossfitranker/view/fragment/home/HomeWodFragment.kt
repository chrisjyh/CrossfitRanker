package com.eunho.crossfitranker.view.fragment.home

import com.eunho.crossfitranker.databinding.FragmentHomeWodBinding

import android.util.Log
import com.eunho.crossfitranker.view.fragment.BaseFragment

class HomeWodFragment :BaseFragment<FragmentHomeWodBinding>(FragmentHomeWodBinding::inflate) {

    override fun onViewCreated() {
        Log.e("test","home_wod")
    }
}