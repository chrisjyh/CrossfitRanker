package com.eunho.crossfitranker.view.fragment.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.eunho.crossfitranker.common.CURRENTBOX
import com.eunho.crossfitranker.common.HOMEPERSINAL
import com.eunho.crossfitranker.common.WodType
import com.eunho.crossfitranker.common.getCurrentDateTimeAsString
import com.eunho.crossfitranker.data.firebase.FirebaseManger
import com.eunho.crossfitranker.data.firebase.WodInsertForm
import com.eunho.crossfitranker.data.room.Wod
import com.eunho.crossfitranker.databinding.DialogInsertWodBinding
import com.eunho.crossfitranker.view.viewmodel.HomeWodViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.protobuf.Empty
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils

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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isDraggable = true
        }
        return dialog
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        groupCode = when(pageType){
            "wod" -> CURRENTBOX
            "free" -> StringUtils.EMPTY
            else -> HOMEPERSINAL
        }

        binding = DialogInsertWodBinding.inflate(inflater, container, false)
        with(binding){
            chipEmom.setOnClickListener {
                chipEmom.isChecked = true
                chipAmrap.isChecked = false
                chipFortime.isChecked = false
                wodType = WodType.EMOM.toString()
            }
            chipAmrap.setOnClickListener {
                chipEmom.isChecked = false
                chipAmrap.isChecked = true
                chipFortime.isChecked = false
                wodType = WodType.AMRAP.toString()
            }
            chipFortime.setOnClickListener {
                chipEmom.isChecked = false
                chipAmrap.isChecked = false
                chipFortime.isChecked = true
                wodType = WodType.FORTIME.toString()
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            val wodTimeCap = etWodTime
            val wodTitle = etWodTitle
            val wodContent = etWod


            btnEnrollWod.setOnClickListener{

                if (wodTimeCap.text.isNullOrBlank()){
                    return@setOnClickListener
                }
                if (wodTitle.text.isNullOrBlank()){
                    return@setOnClickListener
                }
                if (wodContent.text.isNullOrBlank()){
                    return@setOnClickListener
                }
                val firebaseWodInfo = WodInsertForm(
                    title = wodTitle.text.toString(),
                    wod = wodContent.text.toString(),
                    timeCap = wodTimeCap.text.toString(),
                    regDate = getCurrentDateTimeAsString(),
                    groupCode = groupCode,
                    wodType = wodType,
                    partner = "1"
                )

                val roomWodInfo = Wod(
                    wodType = wodTitle.text.toString(),
                    timeCap = wodTimeCap.text.toString(),
                    title = wodTitle.text.toString(),
                    wod = wodContent.text.toString()
                )

                when(pageType){
                    "wod", "free" -> {
                        insertFirebase(firebaseWodInfo)
                    }
                    else -> {
                        insertRoom(roomWodInfo)
                    }
                }

            }
        }
    }

    private fun insertRoom(wod: Wod){

    }

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