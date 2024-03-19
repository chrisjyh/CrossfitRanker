package com.eunho.crossfitranker.view.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.eunho.crossfitranker.R
import com.eunho.crossfitranker.databinding.FragmentHomeBinding
import com.eunho.crossfitranker.view.fragment.BaseFragment
import com.google.android.material.tabs.TabLayout


class HomeFragment :BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val wodFragment = WodFragment()
        val freeFragment = FreeFragment()
        val personalFragment = PersonalFragment()


        with(binding) {
            homeTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?){
                    when(tab?.position){
                        0 -> {
//                            findNavController().navigate(R.id.navigation_home_wod)
                            replaceFragment(wodFragment)
                        }
                        1 -> {
//                            findNavController().navigate(R.id.navigation_home_free)
                            replaceFragment(freeFragment)
                        }
                        2 -> {
                            replaceFragment(personalFragment)
//                            findNavController().navigate(R.id.navigation_home_personal)
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }

        replaceFragment(wodFragment)
        return binding.root
    }

    // 프레그먼트 교체 함수
    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
    override fun onViewCreated() {
        Log.e("test","home")
    }


}

