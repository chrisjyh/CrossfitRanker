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
import com.eunho.crossfitranker.common.RoomDataResult
import com.eunho.crossfitranker.databinding.FragmentHomePersonalBinding
import com.eunho.crossfitranker.repository.RoomRepository
import com.eunho.crossfitranker.view.fragment.BaseFragment
import com.eunho.crossfitranker.view.viewmodel.HomePersonalViewModel
import kotlinx.coroutines.launch

class PersonalFragment : BaseFragment<FragmentHomePersonalBinding>(
    FragmentHomePersonalBinding::inflate
) {

    private val personalViewModel: HomePersonalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                personalViewModel.PersonalWodResult.collect {
                    when (it) {
                        is RoomDataResult.Success -> {

                        }

                        is RoomDataResult.Error -> {

                        }

                        is RoomDataResult.NoConstructor -> {
                            Log.e("test","data 입력 필요")
                        }

                        else -> {
                            Log.e("F-View", "알수 없는 결과 뱔생!")
                        }
                    }
                }
            }
        }

        return binding.root
    }
    override fun onViewCreated() {
        Log.e("test","home - personal")
    }
}