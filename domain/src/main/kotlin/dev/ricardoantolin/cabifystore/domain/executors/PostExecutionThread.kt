package dev.ricardoantolin.cabifystore.domain.executors

import io.reactivex.Scheduler

interface PostExecutionThread {
    fun getScheduler(): Scheduler
}