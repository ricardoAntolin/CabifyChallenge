package dev.ricardoantolin.cabifystorage.data.utils

import org.mockito.ArgumentMatcher
import org.mockito.Mockito
import java.util.function.Predicate

fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}

private fun <T> uninitialized(): T = null as T


fun <T> matches(predicate: Predicate<T>): ArgumentMatcher<T> =
    ArgumentMatcher { predicate.test(it as T) }