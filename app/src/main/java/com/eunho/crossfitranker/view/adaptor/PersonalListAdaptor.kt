package com.eunho.crossfitranker.view.adaptor

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eunho.crossfitranker.data.room.PersonalRecord
import com.eunho.crossfitranker.databinding.ItemWodBinding


/**
 * ToDo
 *  wodRecyclerListAdaptor 참고 후 migrate 진행 필요
 * */
class PersonalListAdaptor:
    RecyclerView.Adapter<PersonalListAdaptor.PersonalViewHolder>() {

    private var wodList = emptyList<PersonalRecord>()

    class PersonalViewHolder(val binding: ItemWodBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalViewHolder {
        return PersonalViewHolder(
            ItemWodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: PersonalViewHolder, position: Int) {
        val wod = wodList[position]
        with(holder.binding) {
            tvWodTitle.text = wod.title
            tvWodRecord.text = wod.record

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setProductList(wods: List<PersonalRecord>) {
        wodList = wods
        notifyDataSetChanged()
    }

    override fun getItemCount() = wodList.size
}