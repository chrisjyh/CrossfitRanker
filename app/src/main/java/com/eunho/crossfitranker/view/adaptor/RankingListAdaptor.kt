package com.eunho.crossfitranker.view.adaptor


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eunho.crossfitranker.data.firebase.WodRankingRecord
import com.eunho.crossfitranker.databinding.ItemRankingBinding

class RankingListAdaptor(): ListAdapter<WodRankingRecord, RecyclerView.ViewHolder>(RankingDiffCallback) {
    companion object {
        val RankingDiffCallback = object : DiffUtil.ItemCallback<WodRankingRecord>() {
            override fun areItemsTheSame(oldItem: WodRankingRecord, newItem: WodRankingRecord): Boolean {
                return oldItem.userNickName == newItem.userNickName
            }
            override fun areContentsTheSame(oldItem: WodRankingRecord, newItem: WodRankingRecord): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RankingViewHolder(
            ItemRankingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is RankingViewHolder){
            val wodRecord = getItem(position) as WodRankingRecord
            holder.bind(wodRecord)
        }
    }
    /**
     * 와드 view holder
     * */
    inner class RankingViewHolder(
        private val binding: ItemRankingBinding
    ) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(data: WodRankingRecord){
            with(binding){
                var ranking = data.ranking.toString()
                when(ranking){
                    "1" -> { ranking = "1ST" }
                    "2" -> { ranking = "2nd"}
                    "3" -> { ranking = "2rd"}
                    else -> {ranking = "${ranking}th"}
                }

                tvRankingItemTitle.text = data.userNickName
                tvRankingItemRecord.text = data.record
                tvRankingGrade.text = ranking.toString()

            }
        }
    }


}

