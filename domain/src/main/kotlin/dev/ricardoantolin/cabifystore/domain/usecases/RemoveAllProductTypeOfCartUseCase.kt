package dev.ricardoantolin.cabifystore.domain.usecases

import dev.ricardoantolin.cabifystore.domain.executors.PostExecutionThread
import dev.ricardoantolin.cabifystore.domain.models.Cart
import dev.ricardoantolin.cabifystore.domain.models.Product
import dev.ricardoantolin.cabifystore.domain.repositories.CartRepository
import io.reactivex.Completable
import javax.inject.Inject

class RemoveAllProductTypeOfCartUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val postExecutionThread: PostExecutionThread
) {
    fun execute(product: Product): Completable {
        return cartRepository.getCart()
            .take(1)
            .map { cart -> Cart(cart.products.filter { it.code != product.code }) }
            .onErrorReturn { Cart() }
            .flatMapCompletable { cartRepository.saveCart(it) }
            .observeOn(postExecutionThread.getScheduler())
    }
}