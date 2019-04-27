package dev.ricardoantolin.cabifystore.scenes.products.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.ricardoantolin.cabifystore.common.BaseViewModel
import dev.ricardoantolin.cabifystore.common.SingleLiveEvent
import io.reactivex.Observable
import javax.inject.Inject

class ProductListViewModel @Inject constructor(

): BaseViewModel<ProductListViewModel.Input, ProductListViewModel.Output>() {
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