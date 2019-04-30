package dev.ricardoantolin.cabifystore.scenes.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import dev.ricardoantolin.cabifystore.R
import dev.ricardoantolin.cabifystore.common.BaseViewModel
import dev.ricardoantolin.cabifystore.common.SingleLiveEvent
import dev.ricardoantolin.cabifystore.domain.usecases.FetchProductsUseCase
import dev.ricardoantolin.cabifystore.extensions.trackError
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val fetchProductsUseCase: FetchProductsUseCase
) : BaseViewModel<SplashViewModel.Input, SplashViewModel.Output>() {

    data class Input(
        val initTrigger: Observable<Unit>
    )

    data class Output(
        val updateProductsObserver: LiveData<Boolean>,
        val errorTracker: SingleLiveEvent<Int>
    )

    override fun transform(input: Input): Output {
        val errorTracker = SingleLiveEvent<Int>()

        val updateObserver = LiveDataReactiveStreams.fromPublisher(
            input.initTrigger
                .flatMap { networkIsReachable() }
                .flatMap { fetchProductsIfNewrworkIsReachable(it, errorTracker) }
                .toFlowable(BackpressureStrategy.BUFFER)
                .trackError(errorTracker)
                .onErrorReturn { false }
        )

        return Output(updateObserver, errorTracker)
    }

    private fun fetchProductsIfNewrworkIsReachable(
        isNetworkAvailable: Boolean,
        errorTracker: SingleLiveEvent<Int>
    ): Observable<Boolean> = if (isNetworkAvailable) {
        fetchProductsUseCase.execute()
            .andThen(Observable.just(true))
    } else {
        errorTracker.postValue(R.string.network_not_reachable)
        Observable.just(false)
    }

    private fun networkIsReachable(): Observable<Boolean> =
        ReactiveNetwork.observeInternetConnectivity()
}