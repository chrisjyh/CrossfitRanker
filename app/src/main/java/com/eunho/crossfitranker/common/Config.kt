package com.eunho.crossfitranker.common

import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

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

val ioDispatchers = CoroutineScope(Dispatchers.IO)

const val CURRENTBOX = "whalemia"
const val USER = "eunho"