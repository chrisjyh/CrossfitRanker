package com.eunho.crossfitranker.common

import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * 태그 및 파일명
 */
typealias FragmentInflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

const val MYPAGE = "mypage"
const val HOME = "home"
const val CHALLENGE = "challenge"
const val WODCOLLECT = "wod"
const val HOMEFREE = "free"
const val HOMEPERSINAL = "personal"

const val FORMATDATE = "yyyy-MM-dd HH:mm:ss"

const val WODID = "wod_id"
const val RECORD = "record"
const val USERID = "user_id"

const val FIELDGROUPCODE = "group_code"

const val DATATITLE = "title"
const val DATAREGDATA = "reg_date"
const val DATAWOD = "wod"
const val DATATIMECAP = "time_cap"
const val DATAWODTYPE = "wod_type"
const val DATAPARTNER = "partner"
const val DATAGROUP = "group_code"
const val DATAUSERID = "user_id"

const val DIALOGWODRECORD = "wodRecordDialog"
const val DIALOGINSERTWOD = "insertWod"

const val RANERDB = "Ranker.db"
const val PATHDB = "database/Ranker.db"

const val CURRENTBOX = "whalemia"
const val USER = "eunho"

const val SUCCESS = "successs"
const val FAIL = "fail"
const val EMPTY = "empty"

// 와드 종류
enum class WodType {
    AMRAP,
    FORTIME,
    EMOM
}

val ioDispatchers = CoroutineScope(Dispatchers.IO)
