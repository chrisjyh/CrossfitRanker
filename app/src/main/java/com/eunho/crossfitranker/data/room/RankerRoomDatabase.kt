package com.eunho.crossfitranker.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.eunho.crossfitranker.common.PATHDB
import com.eunho.crossfitranker.common.RANERDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * room entity 등록 및 생성
 * */
@Database(
    entities = [
        Wod::class,
        Record::class
    ],
    version = 1
)
abstract class RankerRoomDatabase : RoomDatabase(){
    abstract fun generateDAO(): RankerDAO
    companion object{

        private var instance: RankerRoomDatabase? = null
        fun getInstance(context: Context): RankerRoomDatabase {
            return instance ?: synchronized(this){
                instance ?: buildDatabase(context).also{
                    instance = it
                }
            }
        }
        private fun buildDatabase(context: Context): RankerRoomDatabase{
            return Room.databaseBuilder(context.applicationContext,
                RankerRoomDatabase::class.java, RANERDB)
                .createFromAsset(PATHDB)
                .addCallback(RankerRoomCallback(context))
                .build()
        }
    }
}

/**
 * Room 생성후 콜백
 * */
class RankerRoomCallback(private val context: Context): RoomDatabase.Callback(){
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            val dao = RankerRoomDatabase.getInstance(context).generateDAO()
        }
    }
}
