package com.eunho.crossfitranker.data.firebase

data class WodDataForm(
    val id: String = "",
    val title: String = "",
    val wod: String = "",
    val timeCap: String = "",
    val regDate: String = "",
    val groupCode: String = "",
    val wodType: String = "",
    val partner: String = ""
){
    fun toMap() : Map<String, Any> {
        return mapOf(
            "id" to id,
            "title" to title,
            "wod" to wod,
            "time_cap" to timeCap,
            "reg_date" to regDate,
            "group_code" to groupCode,
            "wod_type" to wodType,
            "partner" to partner
        )
    }
}
