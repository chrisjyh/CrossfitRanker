package com.eunho.crossfitranker.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.eunho.crossfitranker.R
import com.eunho.crossfitranker.common.EMPTY
import com.eunho.crossfitranker.common.FAIL
import com.eunho.crossfitranker.common.RECORD
import com.eunho.crossfitranker.common.USER
import com.eunho.crossfitranker.common.commonToast
import com.eunho.crossfitranker.common.getCurrentDateTimeAsString
import com.eunho.crossfitranker.data.firebase.RecordInsertForm
import com.eunho.crossfitranker.databinding.DialogInsertRecordBinding
import com.eunho.crossfitranker.view.viewmodel.HomeWodViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks

/**
 * 와드 기록 등록 페이지
 * */
class WodRecordDialog(
    private val wodId: String
) : DialogFragment() {

    private lateinit var binding: DialogInsertRecordBinding
    private val firebaseDB = Firebase.firestore
    private val homeWodViewModel: HomeWodViewModel by viewModels()

    @OptIn(FlowPreview::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogInsertRecordBinding.inflate(inflater, container, false)

        with(binding){
            btnInsertRecord
                .clicks()
                .onEach {
                    when(insertRecord(etRecordInput.text.toString())){
                        EMPTY -> {
                            commonToast(requireContext(), R.string.toast_wod_record_dialog_empty)
                        }
                        FAIL -> {
                            commonToast(requireContext(), R.string.toast_wod_record_dialog_fail)
                        }
                        else -> {
                            commonToast(requireContext(), R.string.toast_enroll_success)
                        }
                    }
                }
                .debounce { 500 }
                .launchIn(lifecycleScope)
        }
        return binding.root
    }

    /**
     * 기록 등록
     * */
    private fun insertRecord(record: String): String{
        if(record.isEmpty()){
            return EMPTY
        }

        val insertData = RecordInsertForm(
            record,
            wodId,
            USER,
            getCurrentDateTimeAsString()
        ).toMap()
        // fireStore 기록 저장
        val isInsertData = firebaseDB.collection(RECORD)
            .add(insertData)
            .addOnSuccessListener {
                homeWodViewModel.wodListData()
                dismiss()
            }
            .addOnSuccessListener {
                it.id
            }
            .addOnFailureListener {
                FAIL
            }
        return isInsertData.toString()
    }
}
