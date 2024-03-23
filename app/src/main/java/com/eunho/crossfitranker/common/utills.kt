package com.eunho.crossfitranker.common

import java.text.MessageFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 현재 시간과 날짜 반환
 * 형식: 2024-02-01 17:56:41
 */
fun getCurrentDateTimeAsString(): String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())


fun main(){
    print("ㅏ가가가가각 \n asdfadfafsf \n ${getCurrentDateTimeAsString()}")
}
//fun commonToast(messageFormat: MessageFormat) = xxxw