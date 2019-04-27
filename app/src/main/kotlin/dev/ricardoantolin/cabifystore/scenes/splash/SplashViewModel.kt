package dev.ricardoantolin.cabifystore.scenes.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.ricardoantolin.cabifystore.common.BaseViewModel
import dev.ricardoantolin.cabifystore.common.SingleLiveEvent
import dev.ricardoantolin.cabifystore.domain.usecases.UpdateProductsUseCase
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