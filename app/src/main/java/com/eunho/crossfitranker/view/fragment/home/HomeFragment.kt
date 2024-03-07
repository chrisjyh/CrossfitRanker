package com.eunho.crossfitranker.view.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eunho.crossfitranker.databinding.FragmentHomeBinding
import com.eunho.crossfitranker.view.fragment.BaseFragment
import com.google.android.material.tabs.TabLayout

class HomeFragment :BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        with(binding) {
            homeTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?){
                    Log.e("test","selected: ${tab?.position}")
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    Log.e("test","unselected: ${tab?.position}")
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    Log.e("test","reselected: ${tab?.position}")
                }
            })
        }

        return binding.root
    }
    override fun onViewCreated() {
        Log.e("test","home")
    }
}