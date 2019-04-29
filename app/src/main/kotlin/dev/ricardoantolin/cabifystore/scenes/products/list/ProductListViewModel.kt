package dev.ricardoantolin.cabifystore.scenes.products.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import dev.ricardoantolin.cabifystore.common.BaseViewModel
import dev.ricardoantolin.cabifystore.common.SingleLiveEvent
import dev.ricardoantolin.cabifystore.domain.usecases.AddProductToCartUseCase
import dev.ricardoantolin.cabifystore.domain.usecases.GetCartUseCase
import dev.ricardoantolin.cabifystore.domain.usecases.GetProductsUseCase
import dev.ricardoantolin.cabifystore.extensions.trackError
import dev.ricardoantolin.cabifystore.extensions.trackProgress
import dev.ricardoantolin.cabifystore.mappers.asDomain
import dev.ricardoantolin.cabifystore.mappers.asUIModel
import dev.ricardoantolin.cabifystore.models.UICart
import dev.ricardoantolin.cabifystore.models.UIProduct
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import javax.inject.Inject

class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase
): BaseViewModel<ProductListViewModel.Input, ProductListViewModel.Output>() {
    data class Input(
        val initTrigger: Observable<Unit>,
        val addProductToCartTrigger: Observable<UIProduct>
    )

    data class Output(
        val productsObserver: LiveData<List<UIProduct>>,
        val cartObserver: LiveData<UICart>,
        val addProductObserver: LiveData<Boolean>,
        val errorTracker: SingleLiveEvent<Int>,
        val activityTracker: LiveData<Boolean>
    )

    override fun transform(input: Input): Output {
        val errorTracker = SingleLiveEvent<Int>()
        val activityTracker = MutableLiveData<Boolean>()

        val addProductToCart = fun(product: UIProduct) =
            addProductToCartUseCase.execute(product.asDomain())
                .trackError(errorTracker)
                .trackProgress(activityTracker)
                .andThen(Observable.just(true))
                .onErrorReturn { false }

        val productsObservable = LiveDataReactiveStreams.fromPublisher(
            input.initTrigger.toFlowable(BackpressureStrategy.BUFFER).flatMap {
                getProductsUseCase.execute()
                    .asUIModel()
                    .trackError(errorTracker)
                    .onErrorReturn { listOf() }
            }
        )

        val cartObservable = LiveDataReactiveStreams.fromPublisher(
            input.initTrigger.toFlowable(BackpressureStrategy.BUFFER).flatMap {
                getCartUseCase.execute()
                    .asUIModel()
                    .trackError(errorTracker)
                    .onErrorReturn { UICart(listOf(), listOf()) }
            }
        )

        val addProductObservable = LiveDataReactiveStreams.fromPublisher(
            input.addProductToCartTrigger
                .flatMap { addProductToCart(it) }
                .toFlowable(BackpressureStrategy.BUFFER)
        )

        return Output(
            productsObservable,
            cartObservable,
            addProductObservable,
            errorTracker,
            activityTracker
        )
    }
}