package com.example.roomfirebasesync.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.roomfirebasesync.DbSyncApp
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class ReadDataWorker(
    ctx: Context,
    params: WorkerParameters
) : Worker(ctx, params) {

    override fun doWork(): Result {
        val carsRepoRoom = (applicationContext as DbSyncApp).carsRepository
        var carsRepoFirestore = (applicationContext as DbSyncApp).carsRepositoryFirestore

        var outputData = workDataOf()

        runBlocking {
            var roomList = carsRepoRoom.getAllSync()
            var firebaseList = carsRepoFirestore.getAllCars()
            outputData.keyValueMap["room"] = roomList
            outputData.keyValueMap["firebase"] = firebaseList
        }

        return Result.success(outputData)
    }

    suspend fun <T> Flow<List<T>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()
}