package com.eunho.crossfitranker.view.fragment.mypage

import android.util.Log
import com.eunho.crossfitranker.databinding.FragmentMypageBinding
import com.eunho.crossfitranker.view.fragment.BaseFragment

class MyPageFragment : BaseFragment<FragmentMypageBinding>(FragmentMypageBinding::inflate){
    override fun onViewCreated() {
        Log.e("test","mypage")
    }
}