package com.eunho.crossfitranker.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.eunho.crossfitranker.R
import com.eunho.crossfitranker.databinding.FragmentHomeBinding
import com.eunho.crossfitranker.view.fragment.BaseFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.material.tabSelectionEvents

/**
 * 홈 화면
 * 탭 관리
 * */
class HomeFragment : BaseFragment<FragmentHomeBinding>(
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
            // tab 변경
            homeTabLayout
                .tabSelectionEvents()
                .onEach {
                    // tab 선택 될때
                    if(it.tab.isSelected){
                        when(it.tab.position){
                            0 -> { replaceFragment(wodFragment) }
                            1 -> { replaceFragment(freeFragment) }
                            2 -> { replaceFragment(personalFragment) }
                        }
                    }
                }.launchIn(lifecycleScope)
        }
        replaceFragment(wodFragment)
        return binding.root
    }

    override fun onViewCreated() {}

    /**
     * fragment 교체
     */
    private fun replaceFragment(fragment: Fragment) {
        with(requireActivity().supportFragmentManager.beginTransaction()) {
            replace(R.id.fragment_container, fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(fragment.toString())
            commit()
        }
    }
}

