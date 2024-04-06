package com.eunho.crossfitranker.view.fragment.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.eunho.crossfitranker.common.DIALOGINSERTWOD
import com.eunho.crossfitranker.common.HOMEFREE
import com.eunho.crossfitranker.common.HOMEPERSINAL
import com.eunho.crossfitranker.common.RoomDataResult
import com.eunho.crossfitranker.common.WodType
import com.eunho.crossfitranker.common.ioDispatchers
import com.eunho.crossfitranker.data.room.Wod
import com.eunho.crossfitranker.databinding.FragmentHomePersonalBinding
import com.eunho.crossfitranker.repository.RoomRepository
import com.eunho.crossfitranker.view.adaptor.PersonalListAdaptor
import com.eunho.crossfitranker.view.fragment.BaseFragment
import com.eunho.crossfitranker.view.viewmodel.HomePersonalViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PersonalFragment : BaseFragment<FragmentHomePersonalBinding>(
    FragmentHomePersonalBinding::inflate
) {

    private val personalViewModel: HomePersonalViewModel by viewModels()
    private lateinit var personalListAdaptor: PersonalListAdaptor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        personalListAdaptor = PersonalListAdaptor()
        binding.btnEnrollWod.setOnClickListener{
            WodInsertDialog(HOMEPERSINAL).show(childFragmentManager, DIALOGINSERTWOD)
        }

        return binding.root
    }
    override fun onViewCreated() {
        collectFlow()
        personalViewModel.findAllWodList()
        recyclerSetup()
    }

    fun insertWod() {
        val sample = Wod(
            "12min",
            "20240329 CF wod",
            """ pull up 100
                push up 100
                sit up 100
            """.trimIndent(),
            "${WodType.AMRAP}"
            )
        lifecycleScope.launch {
            personalViewModel.insertRecord(sample)
        }
    }

    private fun recyclerSetup() {

        with(binding.lvWod) {
            layoutManager = LinearLayoutManager(context)
            adapter = personalListAdaptor
        }
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                personalViewModel.personalRecordList.collectLatest {
                    when (it) {
                        is RoomDataResult.Success -> {
                            personalListAdaptor.setProductList(it.resultData)
                        }

                        is RoomDataResult.Error -> {}

                        is RoomDataResult.NoConstructor -> {
                            Log.e("test", "no data")
                        }

                        else -> {
                            Log.e("F-View", "알수 없는 결과 뱔생!")
                        }
                    }
                }
            }
        }
    }
}