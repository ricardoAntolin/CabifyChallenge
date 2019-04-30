package dev.ricardoantolin.cabifystore.domain.usecases

import dev.ricardoantolin.cabifystore.domain.executors.PostExecutionThread
import dev.ricardoantolin.cabifystore.domain.models.Cart
import dev.ricardoantolin.cabifystore.domain.repositories.CartRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val postExecutionThread: PostExecutionThread
) {
    fun execute(): Flowable<Cart>{
        return cartRepository.getCart()
            .observeOn(postExecutionThread.getScheduler())
    }
}