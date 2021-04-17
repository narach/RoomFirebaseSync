package com.example.roomfirebasesync.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomfirebasesync.db.dao.CarsDao
import com.example.roomfirebasesync.db.entities.Car
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [Car::class],
    version = 1,
    exportSchema = false
)
abstract class CarsDatabase : RoomDatabase() {

    abstract fun carsDao(): CarsDao

    companion object {
        @Volatile
        private var INSTANCE: CarsDatabase? = null

        fun getDatabase(
            context: Context
        ): CarsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarsDatabase::class.java,
                    "cars_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}