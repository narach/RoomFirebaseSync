package com.example.roomfirebasesync.db.repository

import androidx.annotation.WorkerThread
import com.example.roomfirebasesync.db.dao.CarsDao
import com.example.roomfirebasesync.db.entities.Car
import kotlinx.coroutines.flow.Flow

class CarsRepository(private val carsDao: CarsDao) {

    val carsList: Flow<List<Car>> = carsDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(car: Car) {
        carsDao.insert(car)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(car: Car) {
        carsDao.update(car)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(car: Car) {
        car.id?.let { carId ->
            carsDao.delete(carId)
        }
    }
}