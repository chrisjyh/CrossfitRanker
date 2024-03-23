package com.eunho.crossfitranker.view.fragment.mypage

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.viewModels
import com.eunho.crossfitranker.databinding.FragmentMypageBinding
import com.eunho.crossfitranker.view.adaptor.WodRecyclerListAdaptor
import com.eunho.crossfitranker.view.fragment.BaseFragment
import com.eunho.crossfitranker.view.viewmodel.HomeWodViewModel
import com.eunho.crossfitranker.view.viewmodel.MyPageViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import reactivecircus.flowbinding.android.widget.textChanges

class MyPageFragment : BaseFragment<FragmentMypageBinding>(
    FragmentMypageBinding::inflate
) {

    private val myPageViewModel: MyPageViewModel by viewModels()
    private val wodRecyclerViewAdaptor: WodRecyclerListAdaptor by lazy {
        WodRecyclerListAdaptor()
    }
    override fun onViewCreated() {


    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    suspend fun translateWeight(){
//        val etMypageLb = binding.etMypageLb
//        val etMypageKg = binding.etMypageKg
//
//        val kgFlow = etMypageKg.textChanges().map { it.toString().toDoubleOrNull() }
//        val lbFlow = etMypageLb.textChanges().map { it.toString().toDoubleOrNull() }
//
//        kgFlow.flatMapLatest { kg ->
//            lbFlow.map { lb ->
//                if (kg != null && lb != null) {
//                    Pair(kg, lb)
//                } else {
//                    null
//                }
//            }
//        }.distinctUntilChanged()
//            .collect { (kg, lb) ->
//                if (kg.isNotNull()) {
//                    etMypageLb.setText(String.format("%.2f", kg * 2.20462262185))
//                }
//                if (lb.isNotNull()) {
//                    etMypageKg.setText(String.format("%.2f", lb * 0.45359237))
//                }
//            }
//    }





    private fun observeSetting(){
        // 리뷰 리스트
        myPageViewModel.joinWodList.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                Log.e("test","empty")
            }else{
                wodRecyclerViewAdaptor.submitList(it)
            }
        }
}
}