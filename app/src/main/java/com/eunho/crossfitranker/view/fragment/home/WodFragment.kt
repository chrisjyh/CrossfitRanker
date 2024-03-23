package com.eunho.crossfitranker.view.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
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
        WodRecyclerListAdaptor()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        with(binding){
            btnEnrollWod.setOnClickListener{
                Log.e("test","clicked")
                homeWodViewModel.viewModelScope.launch{
                    homeWodViewModel.insetSampleData()
                }
            }
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
            if(it.isEmpty()){
                Log.e("test","empty")
            }else{
                wodRecyclerViewAdaptor.submitList(it)
            }
        }

    }

}