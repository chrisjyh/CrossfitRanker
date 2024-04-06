package com.eunho.crossfitranker.view.fragment.home

import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.eunho.crossfitranker.common.RECORD
import com.eunho.crossfitranker.common.USER
import com.eunho.crossfitranker.common.commonToast
import com.eunho.crossfitranker.common.getCurrentDateTimeAsString
import com.eunho.crossfitranker.data.firebase.RecordInsertForm
import com.eunho.crossfitranker.databinding.DialogInsertRecordBinding
import com.eunho.crossfitranker.view.viewmodel.HomeWodViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class WodRecordDialog(
    private val wodId: String
) : DialogFragment() {

    private lateinit var binding: DialogInsertRecordBinding
    private val firebaseDB = Firebase.firestore
    private val homeWodViewModel: HomeWodViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DialogInsertRecordBinding.inflate(inflater, container, false)

        with(binding){
            btnInsertRecord.setOnClickListener{
                insertRecord(etRecordInput.text.toString())
            }
        }
        return binding.root
    }

    private fun insertRecord(record: String){
        val insertData = RecordInsertForm(
            record,
            wodId,
            USER,
            getCurrentDateTimeAsString()
        ).toMap()
        val recordInsert = firebaseDB.collection(RECORD)
            .add(insertData)
            .addOnSuccessListener {
                homeWodViewModel.wodListData()
                dismiss()
            }
    }
}
