package com.example.roomfirebasesync.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.roomfirebasesync.db.repository.CarsRepositoryFirestore
import com.example.roomfirebasesync.db.repository.CarsRepositoryRoom
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ReadDataWorker(
    ctx: Context,
    params: WorkerParameters,
    private val carsRepoRoom: CarsRepositoryRoom,
    private val carsRepoFirestore: CarsRepositoryFirestore
) : Worker(ctx, params) {

    override fun doWork(): Result {

        var outputData = workDataOf()

        runBlocking {
            var roomList = carsRepoRoom.carsList.first()
            var firebaseList = carsRepoFirestore.getAllCars()
            outputData.keyValueMap.put("room", roomList)
            outputData.keyValueMap.put("firebase", firebaseList)
        }

        return Result.success(outputData)
    }
}