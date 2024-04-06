package com.eunho.crossfitranker.common

import android.content.Context
import android.widget.Toast
import com.eunho.crossfitranker.databinding.DialogInsertWodBinding
import java.text.MessageFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 현재 시간과 날짜 반환
 * 형식: 2024-02-01 17:56:41
 */
fun getCurrentDateTimeAsString(): String = SimpleDateFormat(FORMATDATE, Locale.getDefault()).format(Date())

fun commonToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}