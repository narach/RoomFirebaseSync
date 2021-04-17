package com.example.roomfirebasesync.db.dao

import androidx.room.*
import com.example.roomfirebasesync.db.entities.Car
import kotlinx.coroutines.flow.Flow

@Dao
interface CarsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(car: Car)

    @Update
    suspend fun update(vararg cars: Car)

    @Query("SELECT * FROM cars ORDER BY id")
    fun getAll() : Flow<List<Car>>

    @Transaction
    @Query("DELETE FROM cars")
    suspend fun deleteAll()

    @Transaction
    @Query("DELETE FROM cars WHERE id = :carId")
    suspend fun delete(carId: Int)
}