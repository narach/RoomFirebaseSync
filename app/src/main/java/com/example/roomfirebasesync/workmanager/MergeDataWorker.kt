package com.example.roomfirebasesync.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.roomfirebasesync.db.entities.Car

class MergeDataWorker(
    ctx: Context,
    params: WorkerParameters
) : Worker(ctx, params)
{
    private val logTag = "MergeDataWorker"

    override fun doWork(): Result {

        var roomList = inputData.keyValueMap["room"] as List<Car>
        var firestoreList = inputData.keyValueMap["firebase"] as List<Car>

        var resultList = mutableListOf<Car>()
        resultList.addAll(roomList)
        resultList.addAll(firestoreList)
        Log.d(logTag, "Result list: $resultList")
        return Result.success()
    }
}