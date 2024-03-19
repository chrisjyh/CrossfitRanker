package com.eunho.crossfitranker.view.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eunho.crossfitranker.databinding.FragmentHomeFreeBinding
import com.eunho.crossfitranker.view.fragment.BaseFragment

class FreeFragment : BaseFragment<FragmentHomeFreeBinding>(
    FragmentHomeFreeBinding::inflate
) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        return binding.root
    }
    override fun onViewCreated() {
        Log.e("test","home - free")
    }
}