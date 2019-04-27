package dev.ricardoantolin.cabifystorage.escenes.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.ricardoantolin.cabifystorage.common.BaseViewModel
import dev.ricardoantolin.cabifystorage.common.SingleLiveEvent
import dev.ricardoantolin.cabifystorage.domain.usecases.UpdateProductsUseCase
import io.reactivex.Observable
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val updateProductsUseCase: UpdateProductsUseCase
): BaseViewModel<SplashViewModel.Input, SplashViewModel.Output>() {

    data class Input(
        val initTrigger: Observable<Void>
    )

    data class Output(
        val updateProductsObserver: LiveData<Boolean>
    )

    override fun transform(input: Input): Output {
        val errorTracker = SingleLiveEvent<Int>()

        return Output(
            MutableLiveData<Boolean>()
        )
    }
}