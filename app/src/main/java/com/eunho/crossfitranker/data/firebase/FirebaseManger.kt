package com.eunho.crossfitranker.data.firebase

import android.annotation.SuppressLint
import com.eunho.crossfitranker.common.CURRENTBOX
import com.eunho.crossfitranker.common.DATAPARTNER
import com.eunho.crossfitranker.common.DATAREGDATA
import com.eunho.crossfitranker.common.DATATIMECAP
import com.eunho.crossfitranker.common.DATATITLE
import com.eunho.crossfitranker.common.DATAUSERID
import com.eunho.crossfitranker.common.DATAWOD
import com.eunho.crossfitranker.common.DATAWODTYPE
import com.eunho.crossfitranker.common.FIELDGROUPCODE
import com.eunho.crossfitranker.common.RECORD
import com.eunho.crossfitranker.common.USER
import com.eunho.crossfitranker.common.WODCOLLECT
import com.eunho.crossfitranker.common.WODID
import com.eunho.crossfitranker.common.ioDispatchers
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.apache.commons.lang3.StringUtils

/**
 * firebase 처리
 * */
class FirebaseManger() {

    private val firebaseDB = Firebase.firestore

    /**
     * 와드 등록
     * */
    suspend fun insertWod(insetWod: WodInsertForm): String {
        val documentId = firebaseDB.collection(WODCOLLECT)
            .add(insetWod.toMap())
            .await()
        return documentId.id
    }

    /**
     * home -> wod -> 와드 제목 리스트
     * */
    @SuppressLint("SuspiciousIndentation")
    suspend fun boxAllWod(): List<WodRecordTitle> {
        // box 코드가 있는
        val allWods = firebaseDB.collection(WODCOLLECT)
            .whereEqualTo(FIELDGROUPCODE, CURRENTBOX)
            .get()
            .await()
        // 참여한 와드의 랭킹과 기록 조회
        val wodRecordList = withContext(ioDispatchers.coroutineContext){
            val wodRecords = mutableListOf<WodRecordTitle>()
                allWods.documents.map { wod ->
                async {
                    val recordDocument = getRecordRanking(wod.id)
                    val myRecord = recordDocument.filter { it.userNickName == USER }

                     wodRecords.add(
                        WodRecordTitle(
                            wodId = wod.id,
                            wodTitle = wod.data!![DATATITLE].toString(),
                            myRecords = myRecord,
                            regDate = wod.data!![DATAREGDATA].toString()
                        )
                    )
                }
            }.awaitAll()
            wodRecords.toList().sortedByDescending { it.regDate }
        }
        return wodRecordList
    }

    /**
     * home -> wod -> 와드 제목 리스트
     * */
    @SuppressLint("SuspiciousIndentation")
    suspend fun freeAllWod(): List<WodRecordTitle> {
        // 전체 와드 조회
        val allWods = firebaseDB.collection(WODCOLLECT)
            .whereEqualTo(FIELDGROUPCODE, StringUtils.EMPTY)
            .get()
            .await()
        val wodRecordList = withContext(ioDispatchers.coroutineContext){
            val wodRecords = mutableListOf<WodRecordTitle>()
            allWods.documents.map { wod ->
                async {
                    val recordDocument = getRecordRanking(wod.id)

                    val myRecord = recordDocument.filter { it.userNickName == USER }

                    wodRecords.add(
                        WodRecordTitle(
                            wodId = wod.id,
                            wodTitle = wod.data!![DATATITLE].toString(),
                            myRecords = myRecord,
                            regDate = wod.data!![DATAREGDATA].toString()
                        )
                    )
                }
            }.awaitAll()
            wodRecords.toList().sortedByDescending { it.regDate }
        }
        return wodRecordList
    }

    /**
     * mypage > 참여한 와드
     * */
    @SuppressLint("SuspiciousIndentation")
    suspend fun joinedAllWod(): List<WodRecordTitle> {
        // 전체 와드
        val allWods = firebaseDB.collection(WODCOLLECT)
            .get()
            .await()
        //
        val wodRecordList = withContext(ioDispatchers.coroutineContext){
            val wodRecords = mutableListOf<WodRecordTitle>()
            allWods.documents.map { wod ->
                async {
                    val recordDocument = getRecordRanking(wod.id)
                    val myRecord = recordDocument.filter { it.userNickName == USER }

                    if(myRecord.isNotEmpty()){
                        wodRecords.add(
                            WodRecordTitle(
                                wodId = wod.id,
                                wodTitle = wod.data!![DATATITLE].toString(),
                                myRecords = myRecord,
                                regDate = wod.data!![DATAREGDATA].toString()
                            )
                        )
                    }
                }
            }.awaitAll()
            wodRecords.toList().sortedByDescending { it.regDate }
        }
        return wodRecordList
    }

    /**
     * 와드 검색
     * */
    suspend fun searchWod(query: String): List<WodRecordTitle> {
        val wod = boxAllWod()

        if(query.isEmpty()){
            return wod
        }

        val filterWod = wod.filter {
            query in it.wodTitle
        }
        return filterWod
    }

    /**
     * 와드 상세 정보
     * */
    suspend fun getWodInfo(wodId: String): WodInsertForm{
        val wodInfo = firebaseDB.collection(WODCOLLECT)
            .document(wodId)
            .get()
            .await()

        return WodInsertForm(
            title = wodInfo.data?.get(DATATITLE).toString(),
            wod = wodInfo.data?.get(DATAWOD).toString(),
            timeCap = wodInfo.data?.get(DATATIMECAP).toString(),
            regDate = wodInfo.data?.get(DATAREGDATA).toString(),
            wodType = wodInfo.data?.get(DATAWODTYPE).toString(),
            partner = wodInfo.data?.get(DATAPARTNER).toString(),
        )
    }

    /**
     * 와드 상세 보기 랭킹
     * */
    suspend fun getRecordRanking(wodId: String): List<WodRankingRecord> {

        val refWodRankingRecord = firebaseDB.collection(RECORD)
            .whereEqualTo(WODID, wodId)
            .get()
            .await()

        val rankingRecords = refWodRankingRecord.documents.map {
            WodRankingRecord(
                ranking = 0,
                record = it.data?.get(RECORD).toString(),
                userNickName = it.data?.get(DATAUSERID).toString()
            )
        }.sortedByDescending { it.record.toInt() }
        // Tutor Pyo Collection Chain으로 변경
        for (i in rankingRecords.indices) {
            rankingRecords[i].ranking = i + 1
        }

        return rankingRecords
    }


}
