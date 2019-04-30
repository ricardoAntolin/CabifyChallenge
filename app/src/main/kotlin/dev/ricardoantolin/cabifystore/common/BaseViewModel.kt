package dev.ricardoantolin.cabifystore.common

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<Input, Output>: ViewModel() {
    abstract fun transform(input: Input): Output
}