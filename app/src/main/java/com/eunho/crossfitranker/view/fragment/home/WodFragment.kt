package com.eunho.crossfitranker.view.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.eunho.crossfitranker.common.DIALOGINSERTWOD
import com.eunho.crossfitranker.common.WODCOLLECT
import com.eunho.crossfitranker.databinding.FragmentHomeWodBinding
import com.eunho.crossfitranker.view.adaptor.WodRecyclerListAdaptor
import com.eunho.crossfitranker.view.fragment.BaseFragment
import com.eunho.crossfitranker.view.viewmodel.HomeWodViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks
import reactivecircus.flowbinding.appcompat.queryTextEvents

/**
 * 홈 > wod
 * 박스에 해당 하는 와드
 * */
class WodFragment : BaseFragment<FragmentHomeWodBinding>(
    FragmentHomeWodBinding::inflate
) {
    private val homeWodViewModel: HomeWodViewModel by viewModels()
    private val wodRecyclerViewAdaptor: WodRecyclerListAdaptor by lazy {
        WodRecyclerListAdaptor(childFragmentManager)
    }

    @OptIn(FlowPreview::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        with(binding){
            // 와드 등록 버튼
            btnEnrollWod
                .clicks()
                .onEach{
                    WodInsertDialog(WODCOLLECT).show(childFragmentManager, DIALOGINSERTWOD)
                }
                // 중복 방지
                .debounce(500L)
                .launchIn(lifecycleScope)

            // 검색
            searchBar
                .queryTextEvents()
                .onEach {
                    searchWod(it.queryText.toString())
                }
                .launchIn(lifecycleScope)
        }
        return binding.root
    }

    override fun onViewCreated() {
        // recycler 및 초기 뷰 세팅
        setupRecycler()
        // 초기 와드 리스트 조회
        homeWodViewModel.wodListData()
        observeWodList()
    }

    /**
     * RecyclerView 세팅
     * */
    private fun setupRecycler(){
        binding.lvWod.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = wodRecyclerViewAdaptor
        }
    }

    /**
     * observe 연결
     * */
    private fun observeWodList(){
        // 리뷰 리스트
        homeWodViewModel.wodLiveData.observe(viewLifecycleOwner){
            wodRecyclerViewAdaptor.submitList(it)
        }
    }

    // 와드 검색
    private fun searchWod(query: String){
        homeWodViewModel.searchWods(query)
    }

}