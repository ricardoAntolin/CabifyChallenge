package dev.ricardoantolin.cabifystore.scenes.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import dev.ricardoantolin.cabifystore.common.BaseViewModel
import dev.ricardoantolin.cabifystore.common.SingleLiveEvent
import dev.ricardoantolin.cabifystore.domain.usecases.AddProductToCartUseCase
import dev.ricardoantolin.cabifystore.domain.usecases.GetCartUseCase
import dev.ricardoantolin.cabifystore.domain.usecases.RemoveAllProductTypeOfCartUseCase
import dev.ricardoantolin.cabifystore.domain.usecases.RemoveProductOfCartUseCase
import dev.ricardoantolin.cabifystore.extensions.trackError
import dev.ricardoantolin.cabifystore.extensions.trackProgress
import dev.ricardoantolin.cabifystore.mappers.asDomain
import dev.ricardoantolin.cabifystore.mappers.asUIModel
import dev.ricardoantolin.cabifystore.models.UICart
import dev.ricardoantolin.cabifystore.models.UIProduct
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val removeProductOfCartUseCase: RemoveProductOfCartUseCase,
    private val removeAllProductTypeOfCartUseCase: RemoveAllProductTypeOfCartUseCase
): BaseViewModel<CartViewModel.Input, CartViewModel.Output>() {
    data class Input(
        val initTrigger: Observable<Unit>,
        val removeOneProductTrigger: Observable<UIProduct>,
        val removeAllProductTrigger: Observable<UIProduct>,
        val addProductTrigger: Observable<UIProduct>
    )

    data class Output(
        val cartObserver: LiveData<UICart>,
        val removeOneProductObserver: LiveData<Boolean>,
        val removeAllProductObserver: LiveData<Boolean>,
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

        val removeOneProductFromCart = fun(product: UIProduct) =
            removeProductOfCartUseCase.execute(product.asDomain())
                .trackError(errorTracker)
                .trackProgress(activityTracker)
                .andThen(Observable.just(true))
                .onErrorReturn { false }

        val removeAllProductTypeFromCart = fun(product: UIProduct) =
            removeAllProductTypeOfCartUseCase.execute(product.asDomain())
                .trackError(errorTracker)
                .trackProgress(activityTracker)
                .andThen(Observable.just(true))
                .onErrorReturn { false }

        val cartObservable = LiveDataReactiveStreams.fromPublisher(
            input.initTrigger.toFlowable(BackpressureStrategy.BUFFER).flatMap {
                getCartUseCase.execute()
                    .asUIModel()
                    .trackError(errorTracker)
                    .onErrorReturn { UICart(listOf(), listOf()) }
            }
        )

        val addProductObservable = LiveDataReactiveStreams.fromPublisher(
            input.addProductTrigger
                .flatMap { addProductToCart(it) }
                .toFlowable(BackpressureStrategy.BUFFER)
        )

        val removeOneProductObservable = LiveDataReactiveStreams.fromPublisher(
            input.removeOneProductTrigger
                .flatMap { removeOneProductFromCart(it) }
                .toFlowable(BackpressureStrategy.BUFFER)
        )

        val removeAllProductTypeObservable = LiveDataReactiveStreams.fromPublisher(
            input.removeAllProductTrigger
                .flatMap { removeAllProductTypeFromCart(it) }
                .toFlowable(BackpressureStrategy.BUFFER)
        )

        return Output(
            cartObservable,
            removeOneProductObservable,
            removeAllProductTypeObservable,
            addProductObservable,
            errorTracker,
            activityTracker
        )
    }
}