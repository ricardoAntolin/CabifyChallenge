package dev.ricardoantolin.cabifystore.domain.usecases

import dev.ricardoantolin.cabifystore.domain.executors.PostExecutionThread
import dev.ricardoantolin.cabifystore.domain.models.Cart
import dev.ricardoantolin.cabifystore.domain.models.Product
import dev.ricardoantolin.cabifystore.domain.repositories.CartRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddProductToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val postExecutionThread: PostExecutionThread
){
    fun execute(product: Product): Completable {
        return cartRepository.getCart()
            .take(1)
            .map { Cart(it.products.toMutableList().apply { add(product) }) }
            .onErrorReturn { Cart() }
            .flatMapCompletable { cartRepository.saveCart(it) }
            .observeOn(postExecutionThread.getScheduler())
    }
}