package com.eunho.crossfitranker.view.fragment.mypage

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.eunho.crossfitranker.databinding.FragmentMypageBinding
import com.eunho.crossfitranker.view.adaptor.WodRecyclerListAdaptor
import com.eunho.crossfitranker.view.fragment.BaseFragment
import com.eunho.crossfitranker.view.viewmodel.MyPageViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.widget.textChanges

/**
 * 마이 페이지
 * */
class MyPageFragment : BaseFragment<FragmentMypageBinding>(
    FragmentMypageBinding::inflate
) {
    private val myPageViewModel: MyPageViewModel by viewModels()
    private val wodRecyclerViewAdaptor: WodRecyclerListAdaptor by lazy {
        WodRecyclerListAdaptor(childFragmentManager)
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated() {
        with(binding){
            // lb -> kg
            etMypageLb
                .textChanges()
                .onEach { text ->
                    if (text.isNotEmpty()) {
                        val number = text.toString().toDouble()
                        val pound = number / 2.205
                        tvKg.text = "%.2f kg".format(pound)
                    } else {
                        tvKg.text = ""
                    }
                }
                .launchIn(lifecycleScope)

            // kg -> lb
            etMypageKg
                .textChanges()
                .onEach { text ->
                    if (text.isNotEmpty()) {
                        val number = text.toString().toDouble()
                        val pound = number * 2.205
                        tvLb.text = "%.2f lb".format(pound)
                    } else {
                        tvLb.text = ""
                    }
                }
                .launchIn(lifecycleScope)
        }
        // 페이지 초기화
        settingMtPage()
    }

    /**
     * 마이 페이지 초기화
     * */
    private fun settingMtPage(){
        setupRecycler()
        myPageViewModel.personalWodListData()
        observeSetting()
    }

    /**
     * recycler adaptor 세팅
     * */
    private fun setupRecycler(){
        binding.rvMypage.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = wodRecyclerViewAdaptor
        }
    }

    /**
     * 옵져버 세팅
     * */
    private fun observeSetting(){
        // 리뷰 리스트
        myPageViewModel.joinWodList.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                wodRecyclerViewAdaptor.submitList(it)
            }
        }
    }
}