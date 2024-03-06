package com.eunho.crossfitranker.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName="wod_info")
class Wod {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "wod_id")
    var wod_id: Int = 0

    @ColumnInfo(name = "time_cap")
    var time_cap: String = ""

    @ColumnInfo(name = "wod_title")
    var title: String = ""

    @ColumnInfo(name = "wod")
    var wod: String = ""

    @ColumnInfo(name = "wod_type")
    var wod_type: String = ""

    @ColumnInfo(name = "reg_date")
    var reg_date: String = ""

    constructor() {}

    @Ignore
    constructor(time_cap: String, title: String, wod:String, wod_type: String, reg_date: String){
        this.time_cap = time_cap
        this.title = title
        this.wod = wod
        this.wod_type = wod_type
        this.reg_date = reg_date
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
    var record_id: Int = 0
    @ColumnInfo(name = "wod_id")
    var wod_id: Int = 0
    @ColumnInfo(name="record")
    var record: String = ""
}

data class LocalWodDashBoard(
    @ColumnInfo(name = "wod_id") var wod_id: Int,
    @ColumnInfo(name = "wod_title") var wod_title: String,
    @ColumnInfo(name = "record") var record: String,
    @ColumnInfo(name = "reg_date") var reg_date: String,
)