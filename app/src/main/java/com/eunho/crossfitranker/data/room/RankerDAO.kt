package com.eunho.crossfitranker.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.eunho.crossfitranker.data.WodRecord

@Dao
interface RankerDAO {
    @Transaction
    @Insert
    fun insertWod(wod: Wod)


    @Query("""
        select 
            w.wod_id as "wod_id",
            w.wod_title as "wod_title",
            r.record as "record"
        from wod w
        left join record r
            on w.wod_id = r.wod_id
        order by w.reg_date DESC
    """)
    fun findAllPersonalRecord():List<PersonalRecord>
}