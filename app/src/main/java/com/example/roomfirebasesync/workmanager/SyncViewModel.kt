package com.example.roomfirebasesync.workmanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.roomfirebasesync.db.repository.CarsRepositoryFirestore
import com.example.roomfirebasesync.db.repository.CarsRepositoryRoom

class SyncViewModel(application: Application,
                    private val carsRepoRoom: CarsRepositoryRoom,
                    private val carsRepoFirestore: CarsRepositoryFirestore)
: AndroidViewModel(application) {

    private val workManager = WorkManager.getInstance(application)
    val outputWorkInfos: LiveData<List<WorkInfo>>

    init {
        outputWorkInfos = workManager.getWorkInfosByTagLiveData("OUTPUT")
    }

    fun applySync() {
        var continuation = workManager.beginUniqueWork(
            "dbSync",
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequest.from(ReadDataWorker::class.java)
        )
    }
}