package dev.ricardoantolin.cabifystorage.common

import dev.ricardoantolin.cabifystorage.R
import timber.log.Timber

class ErrorMessageFactory {
    companion object {
        private val errorCodes = hashMapOf(
            "error.code.from.api" to R.string.some_error
        )

        fun create(exception: Throwable): Int {
            Timber.d(exception)
            return errorCodes[exception.message] ?: R.string.exception_message_generic
        }
    }
}