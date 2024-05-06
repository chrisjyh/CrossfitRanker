package com.eunho.crossfitranker.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.eunho.crossfitranker.common.DATATIMECAP
import com.eunho.crossfitranker.common.DATAWODTYPE
import com.eunho.crossfitranker.common.WODCOLLECT
import com.eunho.crossfitranker.common.WODID
import com.eunho.crossfitranker.common.getCurrentDateTimeAsString
import org.apache.commons.lang3.StringUtils
/**
 * 와드 리스트
 * */
@Entity(tableName= WODCOLLECT)
class Wod {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WODID)
    var wodId: Int = 0

    @ColumnInfo(name = DATAWODTYPE)
    var wodType: String = StringUtils.EMPTY

    @ColumnInfo(name = DATATIMECAP)
    var timeCap: String = StringUtils.EMPTY

    @ColumnInfo(name = "wod_title")
    var title: String = StringUtils.EMPTY

    @ColumnInfo(name = "wod")
    var wod: String = StringUtils.EMPTY

    @ColumnInfo(name = "reg_date")
    var regDate: String = getCurrentDateTimeAsString()

    constructor() {}
    @Ignore
    constructor(timeCap: String, title: String, wod:String, wodType: String){
        this.timeCap = timeCap
        this.title = title
        this.wod = wod
        this.wodType = wodType
    }
}
/**
 * 와드에 대한 기록
 * */
@Entity(
    tableName = "record"
    , foreignKeys = [
        ForeignKey(
            entity = Wod::class,
            parentColumns = ["wod_id"],
            childColumns = ["wod_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
class Record {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "record_id")
    var recordId: Int = 0
    @ColumnInfo(name = "wod_id")
    var wodId: Int = 0
    @ColumnInfo(name="record")
    var record: String = StringUtils.EMPTY
    @ColumnInfo(name = "reg_date")
    var regDate: String = StringUtils.EMPTY
}

/**
 * 개인 기록들
 * */
data class PersonalRecord(
    @ColumnInfo(name = "wod_id") var wodId: Int,
    @ColumnInfo(name = "wod_title") var title: String,
    @ColumnInfo(name = "record") var record: String?
)