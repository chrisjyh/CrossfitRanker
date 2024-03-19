package com.eunho.crossfitranker.data

data class WodRecord(
    var wodId: Int,
    var wodTitle: String,
    var record: List<MyRecord>
)
data class MyRecord(
    var record: Int,
    var score: String
)