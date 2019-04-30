package dev.ricardoantolin.cabifystore.scenes.products.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import dev.ricardoantolin.cabifystore.common.BaseViewModel
import dev.ricardoantolin.cabifystore.common.SingleLiveEvent
import dev.ricardoantolin.cabifystore.domain.usecases.AddProductToCartUseCase
import dev.ricardoantolin.cabifystore.domain.usecases.GetProductsUseCase
import dev.ricardoantolin.cabifystore.extensions.trackError
import dev.ricardoantolin.cabifystore.extensions.trackProgress
import dev.ricardoantolin.cabifystore.mappers.asDomain
import dev.ricardoantolin.cabifystore.mappers.asUIModel
import dev.ricardoantolin.cabifystore.models.UIProduct
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase
): BaseViewModel<ProductDetailViewModel.Input, ProductDetailViewModel.Output>() {
    data class Input(
        val initTrigger: Observable<UIProduct>,
        val addProductToCartTrigger: Observable<UIProduct>
    )

    data class Output(
        val updateProductObserver: LiveData<UIProduct>,
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
            input.initTrigger.toFlowable(BackpressureStrategy.BUFFER).flatMap { product ->
                getProductsUseCase.execute()
                    .map { products -> products.first { it.code == product.code }.asUIModel() }
                    .trackError(errorTracker)
                    .onErrorResumeNext(Flowable.empty<UIProduct>())
            }
        )

        val addProductObservable = LiveDataReactiveStreams.fromPublisher(
            input.addProductToCartTrigger
                .flatMap { addProductToCart(it) }
                .toFlowable(BackpressureStrategy.BUFFER)
        )

        return Output(
            productsObservable,
            addProductObservable,
            errorTracker,
            activityTracker
        )
    }
}
