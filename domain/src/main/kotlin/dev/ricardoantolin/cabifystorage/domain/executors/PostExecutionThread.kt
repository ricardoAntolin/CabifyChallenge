package dev.ricardoantolin.cabifystorage.domain.executors

import io.reactivex.Scheduler

interface PostExecutionThread {
    fun getScheduler(): Scheduler
}