package com.example.roomfirebasesync.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.roomfirebasesync.DbSyncApp
import com.example.roomfirebasesync.db.entities.Car
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
            var resultList = mutableListOf<Car>()
            resultList.addAll(roomList)
            resultList.addAll(firebaseList)

            // Cleanup the Room database
            carsRepoRoom.deleteAll()
            // Cleanup the Firestore database
            carsRepoFirestore.deleteAllCars()

            // Fill in both Room and Firestore database with the same values
            for(car in resultList) {
                carsRepoRoom.insert(car)
                carsRepoFirestore.saveCarFirestore(car)
            }
        }

        return Result.success(outputData)
    }

}