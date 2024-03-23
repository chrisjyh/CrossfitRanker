package com.eunho.crossfitranker.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.eunho.crossfitranker.common.getCurrentDateTimeAsString

@Entity(tableName="wod")
class Wod {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "wod_id")
    var wodId: Int = 0

    @ColumnInfo(name = "wod_type")
    var wodType: String = ""

    @ColumnInfo(name = "time_cap")
    var timeCap: String = ""

    @ColumnInfo(name = "wod_title")
    var title: String = ""

    @ColumnInfo(name = "wod")
    var wod: String = ""

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
    var record: String = ""
    @ColumnInfo(name = "reg_date")
    var regDate: String = ""
}

data class PersonalRecord(
    @ColumnInfo(name = "wod_id") var wodId: Int,
    @ColumnInfo(name = "wod_title") var title: String,
    @ColumnInfo(name = "record") var record: String?
)