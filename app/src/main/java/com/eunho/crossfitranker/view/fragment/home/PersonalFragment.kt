package com.eunho.crossfitranker.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.eunho.crossfitranker.common.DIALOGINSERTWOD
import com.eunho.crossfitranker.common.HOMEPERSINAL
import com.eunho.crossfitranker.common.RoomDataResult
import com.eunho.crossfitranker.databinding.FragmentHomePersonalBinding
import com.eunho.crossfitranker.view.adaptor.PersonalListAdaptor
import com.eunho.crossfitranker.view.fragment.BaseFragment
import com.eunho.crossfitranker.view.viewmodel.HomePersonalViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.view.clicks

/**
 * personal Fragment
 * 개인 와드 페이지
 * */
class PersonalFragment : BaseFragment<FragmentHomePersonalBinding>(
    FragmentHomePersonalBinding::inflate
) {

    private val personalViewModel: HomePersonalViewModel by viewModels()
    private val personalListAdaptor: PersonalListAdaptor by lazy{
        PersonalListAdaptor(childFragmentManager)
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
                WodInsertDialog(HOMEPERSINAL).show(childFragmentManager, DIALOGINSERTWOD)
            }
            // 중복 방지
            .debounce(500L)
            .launchIn(lifecycleScope)

        return binding.root
    }

    override fun onViewCreated() {
        // setting
        collectFlow()
        personalViewModel.findAllWodList()
        recyclerSetup()
    }

    /**
     * recycler 세팅
     * */
    private fun recyclerSetup() {
        with(binding.lvWod) {
            layoutManager = LinearLayoutManager(context)
            adapter = personalListAdaptor
        }
    }

    /**
     * 룸와드 데이터 상태 관리
     * */
    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                // room 와드 데이터 상태 관리
                personalViewModel.personalRecordList.collectLatest {
                    when (it) {
                        is RoomDataResult.Success -> {
                            personalListAdaptor.submitList(it.resultData)
                        }

                        is RoomDataResult.Error -> {}

                        is RoomDataResult.NoConstructor -> {}

                        else -> {}
                    }
                }
            }
        }
    }
}