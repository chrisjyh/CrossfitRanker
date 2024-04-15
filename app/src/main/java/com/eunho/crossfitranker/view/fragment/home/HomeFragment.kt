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
                        0 -> { replaceFragment(wodFragment) }
                        1 -> { replaceFragment(freeFragment) }
                        2 -> { replaceFragment(personalFragment) }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }

        replaceFragment(wodFragment)
        return binding.root
    }
    override fun onViewCreated() {}

    // fragment 교체
    private fun replaceFragment(fragment: Fragment) {
        val transition = requireActivity().supportFragmentManager.beginTransaction()

        transition.replace(R.id.fragment_container, fragment)
        transition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transition.addToBackStack(fragment.toString())
        transition.commit()
    }
}

