package com.eunho.crossfitranker.view.adaptor

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.eunho.crossfitranker.data.WodRecord
import com.eunho.crossfitranker.databinding.ItemWodBinding

class WodViewHolder(
    private val binding: ItemWodBinding
) : RecyclerView.ViewHolder(binding.root){
    @SuppressLint("SetTextI18n")
    fun bind(data: WodRecord){
        with(binding){
            tvWodTitle.text = data.wodTitle

            if(data.record.isEmpty()){
                tvWodRecord.visibility = View.GONE
                btnEnrollRecord.visibility = View.VISIBLE

                btnEnrollRecord.setOnClickListener{
                    // data.wodId 전달 해서 입력 하게 해야 하것지?
                    // todo 바텀 시트로 기록 입력 하게 해야함 || 그냥 인풋 영역 나오게?
                    Log.e("test","scoreInput clicked")
                }


            }else{
                tvWodRecord.visibility = View.VISIBLE
                btnEnrollRecord.visibility = View.GONE

                tvWodRecord.text = """
                    등수: ${data.record[0].record}
                    점수: ${data.record[0].score}
                """.trimIndent()
            }

        }
    }
}

