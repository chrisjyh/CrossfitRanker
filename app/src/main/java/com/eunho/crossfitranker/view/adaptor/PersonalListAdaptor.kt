package com.eunho.crossfitranker.view.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eunho.crossfitranker.data.room.PersonalRecord
import com.eunho.crossfitranker.databinding.ItemWodBinding


/**
 * 개인 와드 리스트 어답터
 * */
class PersonalListAdaptor(
    private val fragmentManger: FragmentManager
): ListAdapter<PersonalRecord, RecyclerView.ViewHolder>(PersonalDiffCallback) {

    // DifUtil 설정
    object PersonalDiffCallback: DiffUtil.ItemCallback<PersonalRecord>(){
        override fun areItemsTheSame(oldItem: PersonalRecord, newItem: PersonalRecord): Boolean {
            return  oldItem.wodId == newItem.wodId
        }
        override fun areContentsTheSame(oldItem: PersonalRecord, newItem: PersonalRecord): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PersonalViewHolder(
            ItemWodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PersonalViewHolder){
            val wodRecord = getItem(position) as PersonalRecord
            holder.bind(wodRecord)
        }
    }

    // Personal view holder
    inner class PersonalViewHolder(
        private val binding: ItemWodBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: PersonalRecord){
            with(binding){
                tvWodTitle.text = data.title
            }
        }
    }
}