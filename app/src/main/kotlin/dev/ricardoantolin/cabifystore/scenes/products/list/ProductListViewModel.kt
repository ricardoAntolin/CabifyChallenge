package dev.ricardoantolin.cabifystore.scenes.products.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import dev.ricardoantolin.cabifystore.common.BaseViewModel
import dev.ricardoantolin.cabifystore.common.SingleLiveEvent
import dev.ricardoantolin.cabifystore.domain.usecases.GetProductsUseCase
import dev.ricardoantolin.cabifystore.extensions.trackError
import dev.ricardoantolin.cabifystore.mappers.asUIModel
import dev.ricardoantolin.cabifystore.models.UIProduct
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import javax.inject.Inject

class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
): BaseViewModel<ProductListViewModel.Input, ProductListViewModel.Output>() {
    data class Input(
        val initTrigger: Observable<Unit>
    )

    data class Output(
        val productsObserver: LiveData<List<UIProduct>>,
        val errorTracker: SingleLiveEvent<Int>
    )

    override fun transform(input: Input): Output {
        val errorTracker = SingleLiveEvent<Int>()

        val productsObservable = LiveDataReactiveStreams.fromPublisher(
            input.initTrigger.toFlowable(BackpressureStrategy.BUFFER).flatMap {
                getProductsUseCase.execute()
                    .asUIModel()
                    .trackError(errorTracker)
                    .onErrorReturn { listOf() }
            }
        )

        return Output(
            productsObservable,
            errorTracker
        )
    }
}