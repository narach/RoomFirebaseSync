package com.example.roomfirebasesync

import android.app.Application
import com.example.roomfirebasesync.db.CarsDatabase
import com.example.roomfirebasesync.db.repository.CarsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class DbSyncApp : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { CarsDatabase.getDatabase(this) }
    val carsRepository by lazy { CarsRepository(database.carsDao()) }
}