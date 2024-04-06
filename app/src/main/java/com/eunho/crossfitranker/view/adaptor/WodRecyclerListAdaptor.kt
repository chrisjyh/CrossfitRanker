package com.eunho.crossfitranker.view.adaptor

import android.R
import android.R.attr.fragment
import android.annotation.SuppressLint
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eunho.crossfitranker.common.DIALOGWODRECORD
import com.eunho.crossfitranker.data.firebase.WodRecordTitle
import com.eunho.crossfitranker.databinding.ItemWodBinding
import com.eunho.crossfitranker.view.fragment.home.WodDetailDialog
import com.eunho.crossfitranker.view.fragment.home.WodRecordDialog


class WodRecyclerListAdaptor(
    private val fragmentManager: FragmentManager
): ListAdapter<WodRecordTitle, RecyclerView.ViewHolder>(WodDiffCallback) {

    companion object {
        val WodDiffCallback = object : DiffUtil.ItemCallback<WodRecordTitle>() {
            override fun areItemsTheSame(oldItem: WodRecordTitle, newItem: WodRecordTitle): Boolean {
                return oldItem.wodId == newItem.wodId
            }
            override fun areContentsTheSame(oldItem: WodRecordTitle, newItem: WodRecordTitle): Boolean {
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
            val wodRecord = getItem(position) as WodRecordTitle
            holder.bind(wodRecord)
        }
    }
    /**
     * 와드 view holder
     * */
    inner class WodViewHolder(
        private val binding: ItemWodBinding
    ) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n", "CommitTransaction")
        fun bind(data: WodRecordTitle){
            with(binding){
                // 다이얼 로그 프레그 먼트 생성
                root.setOnClickListener{
                    val wodDetailDialog = WodDetailDialog(data.wodId)

                    if (!fragmentManager.isDestroyed) {
                        wodDetailDialog.show(fragmentManager, "wodDetailDialog")
                    } else{
                        // ToDo 프레그먼트가 파괴되었을때 재생성
                        fragmentManager.beginTransaction()
                    }
                }
                tvWodTitle.text = data.wodTitle

                if(data.myRecords.isEmpty()){
                    tvWodRecord.visibility = View.GONE
                    btnEnrollRecord.visibility = View.VISIBLE

                    // 기록 등록
                    btnEnrollRecord.setOnClickListener{
                        val recordDialog = WodRecordDialog(data.wodId)
                        recordDialog.show(fragmentManager, DIALOGWODRECORD)
                    }
                }else{
                    tvWodRecord.visibility = View.VISIBLE
                    btnEnrollRecord.visibility = View.GONE

                    tvWodRecord.text = """
                    등수: ${data.myRecords[0].ranking}
                    점수: ${data.myRecords[0].record}
                """.trimIndent()
                }

                // 리스트 상단 선
                val divider = View(itemView.context)
                divider.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1
                )
                divider.setBackgroundColor(itemView.context.resources.getColor(R.color.background_dark))

                root.addView(divider)

            }
        }
    }



}

