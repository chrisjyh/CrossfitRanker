package com.eunho.crossfitranker.view.adaptor

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eunho.crossfitranker.data.WodRecord
import com.eunho.crossfitranker.databinding.ItemWodBinding

class WodRecyclerListAdaptor: ListAdapter<WodRecord, RecyclerView.ViewHolder>(WodDiffCallback) {

    companion object {
        val WodDiffCallback = object : DiffUtil.ItemCallback<WodRecord>() {
            override fun areItemsTheSame(oldItem: WodRecord, newItem: WodRecord): Boolean {
                return oldItem.wodId == newItem.wodId
            }
            override fun areContentsTheSame(oldItem: WodRecord, newItem: WodRecord): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WodViewHolder(
            ItemWodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is WodViewHolder){
            val wodRecord = getItem(position) as WodRecord
            holder.bind(wodRecord)
        }
    }
    /**
     * 와드 view holder
     * */
    inner class WodViewHolder(
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

}
