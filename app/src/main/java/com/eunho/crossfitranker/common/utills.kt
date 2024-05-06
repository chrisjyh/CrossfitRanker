package com.eunho.crossfitranker.common

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.children
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * 현재 시간과 날짜 반환
 * 형식: 2024-02-01 17:56:41
 */
@SuppressLint("NewApi")
fun getCurrentDateTimeAsString(): String {
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern(FORMATDATE)
    return now.format(formatter)
}

/**
 * 토스트 메세지
 * */
fun commonToast(context: Context, message: Int) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

/**
 * EditText 빈값 체크
 * */
fun checkForNullEditTexts(view: View): Boolean {
    if (view is EditText) {
        if (view.text.isNullOrBlank()) {
            // 빈값인 에디트 텍스트 포커싱
            view.requestFocus()
            return false
        }
    } else if (view is ViewGroup) {
        for (child in view.children) {
            if (!checkForNullEditTexts(child)) {
                return false
            }
        }
    }
    return true
}