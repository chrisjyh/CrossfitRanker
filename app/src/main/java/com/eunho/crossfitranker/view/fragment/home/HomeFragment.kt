package com.eunho.crossfitranker.view.fragment.home

import android.util.Log
import com.eunho.crossfitranker.databinding.FragmentHomeBinding
import com.eunho.crossfitranker.view.fragment.BaseFragment

class HomeFragment :BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onViewCreated() {
        Log.e("test","home")
    }
}