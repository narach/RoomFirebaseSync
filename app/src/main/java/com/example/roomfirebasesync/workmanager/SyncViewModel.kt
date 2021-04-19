package com.example.roomfirebasesync.workmanager

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
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
        var requestBuilder = OneTimeWorkRequest.Builder(ReadDataWorker::class.java)
        var readDataRequest = requestBuilder.build()
        var continuation = workManager.beginWith(readDataRequest)
//        var mergeDataRequest = OneTimeWorkRequest.Builder(MergeDataWorker::class.java).build()
//        continuation = continuation.then(mergeDataRequest)
//        var saveDataWorker = OneTimeWorkRequest.from(SaveDataWorker::class.java)
//        continuation = continuation.then(saveDataWorker)
        continuation.enqueue()
    }
}

class SyncViewModelFactory(
    private val application: Application,
    private val repositoryRoom: CarsRepositoryRoom,
    private val repositoryFirestore: CarsRepositoryFirestore
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SyncViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SyncViewModel(application, repositoryRoom, repositoryFirestore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}