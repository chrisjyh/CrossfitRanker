package com.eunho.crossfitranker.view.fragment.challenge

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eunho.crossfitranker.R
import com.eunho.crossfitranker.databinding.FragmentChallengeBinding
import com.eunho.crossfitranker.view.fragment.BaseFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks


/**
 * 스쿼트 챌린지 페이지
 * */
class ChallengeFragment : BaseFragment<FragmentChallengeBinding>(
    FragmentChallengeBinding::inflate
) {
    override fun onViewCreated() {
        val cameraFragment = ChallengeCameraFragment()
        val navController = findNavController()
        with(binding){
            // 스쿼트 시작
            btnSquatStart
                .clicks()
                .onEach {
                    navController.navigate(R.id.navigation_challenge_camera)
                }
                .launchIn(lifecycleScope)
        }
    }
}