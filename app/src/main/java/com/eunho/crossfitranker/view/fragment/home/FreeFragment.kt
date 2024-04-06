package com.eunho.crossfitranker.view.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.eunho.crossfitranker.common.DIALOGINSERTWOD
import com.eunho.crossfitranker.common.HOMEFREE
import com.eunho.crossfitranker.common.WODCOLLECT
import com.eunho.crossfitranker.databinding.FragmentHomeFreeBinding
import com.eunho.crossfitranker.view.adaptor.WodRecyclerListAdaptor
import com.eunho.crossfitranker.view.fragment.BaseFragment
import com.eunho.crossfitranker.view.viewmodel.HomeWodViewModel

class FreeFragment : BaseFragment<FragmentHomeFreeBinding>(
    FragmentHomeFreeBinding::inflate
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
        binding.btnEnrollWod.setOnClickListener{
            WodInsertDialog(HOMEFREE).show(childFragmentManager, DIALOGINSERTWOD)
        }

        return binding.root
    }
    override fun onViewCreated() {
        setupRecycler()
        homeWodViewModel.freeListData()
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
        homeWodViewModel.freeLiveData.observe(viewLifecycleOwner){
            wodRecyclerViewAdaptor.submitList(it)
        }
    }

}