package com.example.roomfirebasesync.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class MergeDataWorker(
    ctx: Context,
    params: WorkerParameters
) : Worker(ctx, params)
{
    override fun doWork(): Result {
        return Result.success()
    }
}