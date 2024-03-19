package com.eunho.crossfitranker.view.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eunho.crossfitranker.data.WodRecord
import com.eunho.crossfitranker.databinding.ItemWodBinding

class WodRecyclerListAdaptor: ListAdapter<WodRecord, RecyclerView.ViewHolder>(WodDiffCallback()) {
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

}
class WodDiffCallback : DiffUtil.ItemCallback<WodRecord>(){
    override fun areItemsTheSame(oldItem: WodRecord, newItem: WodRecord): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: WodRecord, newItem: WodRecord): Boolean {
        return oldItem.wodId == newItem.wodId
    }

}