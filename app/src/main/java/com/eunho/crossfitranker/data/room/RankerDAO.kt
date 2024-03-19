package com.eunho.crossfitranker.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.eunho.crossfitranker.data.WodRecord

@Dao
interface RankerDAO {
//    @Query("""
//
//    """)
//    fun getLocalWod(): List<WodRecord>

    @Transaction
    @Insert
    fun insertWod(wod: Wod)
}