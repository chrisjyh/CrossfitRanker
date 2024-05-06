package com.eunho.crossfitranker.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.eunho.crossfitranker.common.DIALOGINSERTWOD
import com.eunho.crossfitranker.common.HOMEFREE
import com.eunho.crossfitranker.databinding.FragmentHomeFreeBinding
import com.eunho.crossfitranker.view.adaptor.WodRecyclerListAdaptor
import com.eunho.crossfitranker.view.fragment.BaseFragment
import com.eunho.crossfitranker.view.viewmodel.HomeWodViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks

/**
 * Free fragment
 */
class FreeFragment : BaseFragment<FragmentHomeFreeBinding>(
    FragmentHomeFreeBinding::inflate
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
        // 와드 등록
        binding.btnEnrollWod
            .clicks()
            .onEach{
                // 와드 등록 다이얼 로그
                WodInsertDialog(HOMEFREE).show(childFragmentManager, DIALOGINSERTWOD)
            }
            .debounce(500L)
            .launchIn(lifecycleScope)

        return binding.root
    }

    override fun onViewCreated() {
        // 페이지 초기 세팅
        setupRecycler()
        homeWodViewModel.freeListData()
        observeWodList()
    }

    /**
     * recycler 세팅
     * */
    private fun setupRecycler(){
        binding.lvWod.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = wodRecyclerViewAdaptor
        }
    }

    /**
     * observe 관리
     * */
    private fun observeWodList(){
        homeWodViewModel.freeLiveData.observe(viewLifecycleOwner){
            wodRecyclerViewAdaptor.submitList(it)
        }
    }

}