package dev.ricardoantolin.cabifystorage.data.executors

import io.reactivex.Scheduler
import java.util.concurrent.Executor

interface ThreadExecutor: Executor {
    fun getScheduler(): Scheduler
}