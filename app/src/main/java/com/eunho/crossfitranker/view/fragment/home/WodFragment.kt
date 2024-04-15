package com.eunho.crossfitranker.view.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.eunho.crossfitranker.common.DIALOGINSERTWOD
import com.eunho.crossfitranker.common.WODCOLLECT
import com.eunho.crossfitranker.databinding.FragmentHomeWodBinding
import com.eunho.crossfitranker.view.adaptor.WodRecyclerListAdaptor
import com.eunho.crossfitranker.view.fragment.BaseFragment
import com.eunho.crossfitranker.view.viewmodel.HomeWodViewModel
import kotlinx.coroutines.launch

class WodFragment : BaseFragment<FragmentHomeWodBinding>(
    FragmentHomeWodBinding::inflate
) {

    private val homeWodViewModel: HomeWodViewModel by viewModels()
    private val wodRecyclerViewAdaptor: WodRecyclerListAdaptor by lazy {
        WodRecyclerListAdaptor(childFragmentManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        with(binding){
            // 바텀 다이얼 로그
            btnEnrollWod.setOnClickListener{
                WodInsertDialog(WODCOLLECT).show(childFragmentManager, DIALOGINSERTWOD)
            }

            // 검색창
            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        searchWod(query)
                    }else{
                        homeWodViewModel.wodListData()
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }

        return binding.root
    }

    override fun onViewCreated() {
        // recycler 및 초기 뷰 세팅
        setupRecycler()
        homeWodViewModel.wodListData()
        observeWodList()
    }

    private fun setupRecycler(){
        binding.lvWod.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = wodRecyclerViewAdaptor
        }
    }

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