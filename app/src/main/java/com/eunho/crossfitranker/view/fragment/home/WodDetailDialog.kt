package com.eunho.crossfitranker.view.fragment.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.eunho.crossfitranker.data.firebase.FirebaseManger
import com.eunho.crossfitranker.databinding.DialogFragmentWodDetailBinding
import com.eunho.crossfitranker.view.adaptor.RankingListAdaptor
import com.eunho.crossfitranker.view.viewmodel.HomeWodViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class WodDetailDialog(
    private val wodId: String
) : BottomSheetDialogFragment() {


    lateinit var binding: DialogFragmentWodDetailBinding
    private val firebaseManager by lazy {
        FirebaseManger()
    }
    private val rankingListAdaptor: RankingListAdaptor by lazy {
        RankingListAdaptor()
    }
    private val homeWodViewModel: HomeWodViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme).apply {

            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isDraggable = false
        }

        return dialog
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentWodDetailBinding.inflate(inflater, container, false)  // 바인딩 초기화

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingWodInfo()
        setupRecycler()
        observeRecordRanking()
    }

    @SuppressLint("SetTextI18n")
    private fun settingWodInfo(){
        homeWodViewModel.viewModelScope.launch {
            val wodDetail = firebaseManager.getWodInfo(wodId)
            with(binding){
                topAppBar.title = wodDetail.title
                tvWodBoard.text = """
                    |${wodDetail.wodType}
                    |
                    |time cap : ${wodDetail.timeCap}
                    |
                    |${wodDetail.wod}
                """.trimMargin()
            }
        }
        homeWodViewModel.getRecordRanking(wodId)
    }


    private fun setupRecycler(){
        binding.rvRanking.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = rankingListAdaptor
        }
    }
    private fun observeRecordRanking(){
        // 리뷰 리스트
        homeWodViewModel.rankingRecord.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                rankingListAdaptor.submitList(it)
            }
        }
    }


}