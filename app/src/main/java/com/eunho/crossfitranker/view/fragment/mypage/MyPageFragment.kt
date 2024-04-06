package com.eunho.crossfitranker.view.fragment.mypage

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.eunho.crossfitranker.databinding.FragmentMypageBinding
import com.eunho.crossfitranker.view.adaptor.WodRecyclerListAdaptor
import com.eunho.crossfitranker.view.fragment.BaseFragment
import com.eunho.crossfitranker.view.viewmodel.MyPageViewModel

class MyPageFragment : BaseFragment<FragmentMypageBinding>(
    FragmentMypageBinding::inflate
) {

    private val myPageViewModel: MyPageViewModel by viewModels()
    private val wodRecyclerViewAdaptor: WodRecyclerListAdaptor by lazy {
        WodRecyclerListAdaptor(childFragmentManager)
    }
    override fun onViewCreated() {
        with(binding){
            etMypageLb.addTextChangedListener(object : TextWatcher {
                @SuppressLint("SetTextI18n")
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val text = s.toString()
                    if (text.isNotEmpty()) {
                        val number = text.toDouble()
                        val pound = number / 2.205
                        tvKg.text = "%.2f kg".format(pound)
                    } else {
                        tvKg.text = ""
                    }
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable) {}
            })

            etMypageKg.addTextChangedListener(object : TextWatcher {
                @SuppressLint("SetTextI18n")
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val text = s.toString()
                    if (text.isNotEmpty()) {
                        val number = text.toDouble()
                        val pound = number * 2.205
                        tvLb.text = "%.2f lb".format(pound)
                    } else {
                        tvLb.text = ""
                    }
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun afterTextChanged(s: Editable) {}
            })
        }
        setupRecycler()
        myPageViewModel.personalWodListData()
        observeSetting()

    }


    private fun setupRecycler(){
        binding.rvMypage.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = wodRecyclerViewAdaptor
        }
    }

    private fun observeSetting(){
        // 리뷰 리스트
        myPageViewModel.joinWodList.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                wodRecyclerViewAdaptor.submitList(it)
            }
        }
    }
}