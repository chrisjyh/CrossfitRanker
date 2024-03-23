package com.eunho.crossfitranker.data.firebase

import android.util.Log
import com.eunho.crossfitranker.common.FireBaseDataResult
import com.eunho.crossfitranker.common.USER
import com.eunho.crossfitranker.common.getCurrentDateTimeAsString
import com.eunho.crossfitranker.common.ioDispatchers
import com.eunho.crossfitranker.data.MyRecord
import com.eunho.crossfitranker.data.WodRecord
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

const val TAG = "test"
class FirebaseManger() {

    private val firebaseDB = Firebase.firestore

    private val sampleData = arrayListOf<WodRecord>().apply {
        add(WodRecord(1,"240316 crossfit wod 월", listOf(MyRecord(1,"100"))))
        add(WodRecord(2,"240317 Ditefit wod 화", listOf(MyRecord(2,"50"))))
        add(WodRecord(3,"240318 crossfit wod 수", listOf(MyRecord(3,"10"))))
        add(WodRecord(9,"240319 crossfit wod 목", listOf()))
        add(WodRecord(4,"240319 crossfit wod 목", listOf(MyRecord(4,"1010"))))
        add(WodRecord(5,"240320 crossfit wod 금", listOf(MyRecord(1,"1220"))))
        add(WodRecord(6,"240321 crossfit wod 토", listOf(MyRecord(3,"1100"))))
        add(
            WodRecord(7,"240323 crossfit wod 오늘은 아주 아주 힘드니 각오 하는게 좋을 겁니다 와드가 아주 기이이이이이일어요", listOf(
                MyRecord(7,"1011")
            ))
        )
        add(WodRecord(8,"240324 crossfit wod 일", listOf(MyRecord(1,"10313"))))
    }

    val insertSample = hashMapOf(
        "title" to "240321 cf wod",
        "wod" to """
                5 Wall Walk
                50 DU
                15 KBS (24/16)
                15/12Cal Machine
            """.trimIndent(),
        "time_cap" to "20min",
        "reg_date" to getCurrentDateTimeAsString(),
        "group_code" to "",
        "wod_type" to "EMOM",
        "partner" to 1
    )

    fun insertWod() {
        val documentId = firebaseDB.collection("wod")
            .add(insertSample)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }

    suspend fun boxAllWod(box: String): ArrayList<WodRecord> {

        val allWods = firebaseDB.collection("wod")
            .whereEqualTo("group_code", box)
            .get()
            .await()

        val wodRecordList = withContext(ioDispatchers.coroutineContext){
            allWods.documents.map { wod ->
                async {
                    val recordDocument = ownerInWodRecord(wod.id)
                    Pair(wod, recordDocument)
                }
            }.awaitAll()
        }

        return sampleData
    }

    private fun ownerInWodRecord(wodId: String) =
        firebaseDB.collection("record")
            .whereEqualTo("wod_id", wodId)
            .whereEqualTo("user_id", USER)
            .get()

    fun findAllWod() = flow {
        val allWods = firebaseDB.collection("wod")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data.get("title")}")
//                    add(WodRecord(1,"240316 crossfit wod 월", listOf(MyRecord(1,"100"))))

//                    WodRecord(document.id,document.data.get("title"),)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        emit(FireBaseDataResult.Success(sampleData))
    }.catch { exception -> FireBaseDataResult.Error(exception) }

}
