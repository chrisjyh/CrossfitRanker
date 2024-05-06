package com.eunho.crossfitranker.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction


/**
 * DAO
 * 개인 기록 ROOM
 * */
@Dao
interface RankerDAO {
    /**
     * 와드 등록
     * */
    @Transaction
    @Insert
    fun insertWod(wod: Wod)


    /**
     * 와드 리스트
     * */
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