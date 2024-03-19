package com.eunho.crossfitranker.common

import android.view.LayoutInflater
import android.view.ViewGroup

typealias FragmentInflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

const val MYPAGE = "mypage"
const val HOME = "home"
const val CHALLENGE = "challenge"


enum class WodType {
    AMRAP,
    FORTIME,
    EMOM,
    IGOYOUGO
}