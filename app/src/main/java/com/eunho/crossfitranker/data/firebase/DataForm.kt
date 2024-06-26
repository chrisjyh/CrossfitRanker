package com.eunho.crossfitranker.data.firebase

import com.eunho.crossfitranker.common.CURRENTBOX
import com.eunho.crossfitranker.common.DATAGROUP
import com.eunho.crossfitranker.common.DATAPARTNER
import com.eunho.crossfitranker.common.DATAREGDATA
import com.eunho.crossfitranker.common.DATATIMECAP
import com.eunho.crossfitranker.common.DATATITLE
import com.eunho.crossfitranker.common.DATAWOD
import com.eunho.crossfitranker.common.DATAWODTYPE
import com.eunho.crossfitranker.common.RECORD
import com.eunho.crossfitranker.common.USERID
import com.eunho.crossfitranker.common.WODID
/**
 *  와드 등록 형식
 */
data class WodInsertForm(
    val title: String = "",
    val wod: String = "",
    val timeCap: String = "",
    val regDate: String = "",
    val groupCode: String = CURRENTBOX,
    val wodType: String = "",
    val partner: String = ""
){
    fun toMap() : Map<String, Any> {
        return mapOf(
            DATATITLE to title,
            DATAWOD to wod,
            DATATIMECAP to timeCap,
            DATAREGDATA to regDate,
            DATAGROUP to groupCode,
            DATAWODTYPE to wodType,
            DATAPARTNER to partner
        )
    }
}

/**
 * 와드 별 기록 등록 형식
 * */
data class RecordInsertForm(
    val record: String,
    val wodId: String,
    val userId: String,
    val regDate: String
){
    fun toMap() : Map<String, Any> {
        return mapOf(
            RECORD to record,
            WODID to wodId,
            USERID to userId,
            DATAREGDATA to regDate,
        )
    }
}


/**
 * 본인의 기록과 랭킹
 * */
data class WodRankingRecord(
    var ranking: Int,
    var record: String,
    var userNickName: String,
)

/**
 * 와드 리스트 표기
 * */
data class WodRecordTitle(
    var wodId: String,
    var wodTitle: String,
    var regDate: String,
    var myRecords: List<WodRankingRecord>
)

