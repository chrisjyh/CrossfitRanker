package com.eunho.crossfitranker.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.eunho.crossfitranker.common.CURRENTBOX
import com.eunho.crossfitranker.common.HOMEPERSINAL
import com.eunho.crossfitranker.common.WodType
import com.eunho.crossfitranker.common.checkForNullEditTexts
import com.eunho.crossfitranker.common.getCurrentDateTimeAsString
import com.eunho.crossfitranker.data.firebase.FirebaseManger
import com.eunho.crossfitranker.data.firebase.WodInsertForm
import com.eunho.crossfitranker.data.room.Wod
import com.eunho.crossfitranker.databinding.DialogInsertWodBinding
import com.eunho.crossfitranker.view.viewmodel.HomeWodViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils
import reactivecircus.flowbinding.android.view.clicks

/**
 * 와드 등록
 * 바텀 다이얼 로그
 * */
class WodInsertDialog(
    private val pageType: String
) : BottomSheetDialogFragment() {

    lateinit var binding: DialogInsertWodBinding
    private val firebaseManager by lazy {
        FirebaseManger()
    }
    private val homeWodViewModel: HomeWodViewModel by viewModels()
    private var wodType = WodType.AMRAP.toString()
    private lateinit var groupCode: String

    override fun onCreateDialog(savedInstanceState: Bundle?) = BottomSheetDialog(requireContext(), theme).apply {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable = true
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // 그룹 코드
        groupCode = when(pageType){
            "wod" -> CURRENTBOX
            "free" -> StringUtils.EMPTY
            else -> HOMEPERSINAL
        }

        binding = DialogInsertWodBinding.inflate(inflater, container, false)

        with(binding) {

            // 와드 타입 chips 라디오
            merge(
                chipAmrap.clicks().map { WodType.AMRAP },
                chipEmom.clicks().map { WodType.EMOM },
                chipFortime.clicks().map { WodType.FORTIME }
            )
                // 반복 적인 emit 피하기 위해
                .distinctUntilChanged()
                .onEach { type ->
                    chipWodTypeWrap.clearCheck()
                    val correspondingChip = when (type) {
                        WodType.AMRAP -> chipAmrap
                        WodType.EMOM -> chipEmom
                        WodType.FORTIME -> chipFortime
                    }
                    correspondingChip.isChecked = true
                    wodType = correspondingChip.text.toString()
                }
                .launchIn(lifecycleScope)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){

            btnEnrollWod
                .clicks()
                .onEach{
                    if(checkForNullEditTexts(binding.root)){
                        // 파이어 베이스 전송 양식
                        val firebaseWodInfo = WodInsertForm(
                            title = etWodTitle.text.toString(),
                            wod = etWod.text.toString(),
                            timeCap = etWodTime.text.toString(),
                            regDate = getCurrentDateTimeAsString(),
                            groupCode = groupCode,
                            wodType = wodType,
                            partner = "1"
                        )

                        // 룸 저장 양식
                        val roomWodInfo = Wod(
                            wodType = etWodTitle.text.toString(),
                            timeCap = etWodTime.text.toString(),
                            title = etWodTitle.text.toString(),
                            wod = etWod.text.toString()
                        )

                        // 부모 페이지 상태 감지
                        when(pageType){
                            "wod", "free" -> {
                                insertFirebase(firebaseWodInfo)
                            }
                            else -> {
                                insertRoom(roomWodInfo)
                            }
                        }
                    }
            }.launchIn(lifecycleScope)
        }
    }

    /**
     * personal 탭에서 등록
     * */
    private fun insertRoom(wod: Wod){

    }


    /**
     * 와드 등록
     * */
    private fun insertFirebase(wodInfo: WodInsertForm){
        lifecycleScope.launch {
            val responseDialogInsert = async { firebaseManager.insertWod(wodInfo) }.await()

            if (responseDialogInsert.isNotEmpty()){
                homeWodViewModel.wodListData()
                dismiss()
            }
        }
    }
    override fun dismiss() {
        super.dismiss()
    }
}