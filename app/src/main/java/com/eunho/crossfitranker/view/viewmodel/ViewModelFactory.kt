package com.eunho.crossfitranker.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eunho.crossfitranker.repository.RankRepository
import com.eunho.crossfitranker.view.fragment.home.HomeFragment

@Suppress("UNCHECKED_CAST")
class ViewModelFactory (): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(HomeFragment::class.java) -> RankViewModel(RankRepository())

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}