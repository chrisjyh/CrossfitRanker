package com.eunho.crossfitranker.view.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.eunho.crossfitranker.data.MyRecord
import com.eunho.crossfitranker.data.WodRecord
import com.eunho.crossfitranker.databinding.FragmentHomeWodBinding
import com.eunho.crossfitranker.view.adaptor.WodRecyclerListAdaptor
import com.eunho.crossfitranker.view.fragment.BaseFragment

class WodFragment : BaseFragment<FragmentHomeWodBinding>(
    FragmentHomeWodBinding::inflate
) {
    private val sampleData = arrayListOf<WodRecord>().apply {
        add(WodRecord(1,"240316 crossfit wod 월", listOf(MyRecord(1,"100"))))
        add(WodRecord(2,"240317 Ditefit wod 화", listOf(MyRecord(2,"50"))))
        add(WodRecord(3,"240318 crossfit wod 수", listOf(MyRecord(3,"10"))))
        add(WodRecord(9,"240319 crossfit wod 목", listOf()))
        add(WodRecord(4,"240319 crossfit wod 목", listOf(MyRecord(4,"1010"))))
        add(WodRecord(5,"240320 crossfit wod 금", listOf(MyRecord(1,"1220"))))
        add(WodRecord(6,"240321 crossfit wod 토", listOf(MyRecord(3,"1100"))))
        add(WodRecord(7,"240323 crossfit wod 오늘은 아주 아주 힘드니 각오 하는게 좋을 겁니다 와드가 아주 기이이이이이일어요", listOf(MyRecord(7,"1011"))))
        add(WodRecord(8,"240324 crossfit wod 일", listOf(MyRecord(1,"10313"))))
    }

    private val wodRecyclerViewAdaptor: WodRecyclerListAdaptor by lazy {
        WodRecyclerListAdaptor()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.lvWod.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = wodRecyclerViewAdaptor
        }
        wodRecyclerViewAdaptor.submitList(sampleData)

        return binding.root
    }
    override fun onViewCreated() {
        Log.e("test","home - wod")
    }
}