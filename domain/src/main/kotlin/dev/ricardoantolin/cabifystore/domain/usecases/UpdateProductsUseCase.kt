package dev.ricardoantolin.cabifystore.domain.usecases

import dev.ricardoantolin.cabifystore.domain.executors.PostExecutionThread
import dev.ricardoantolin.cabifystore.domain.repositories.ProductsRepository
import io.reactivex.Completable
import javax.inject.Inject

class UpdateProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val postExecutionThread: PostExecutionThread
) {
    fun execute(): Completable = productsRepository.updateProducts()
            .observeOn(postExecutionThread.getScheduler())
}