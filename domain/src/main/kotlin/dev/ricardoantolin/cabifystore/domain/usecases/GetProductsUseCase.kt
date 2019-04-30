package dev.ricardoantolin.cabifystore.domain.usecases

import dev.ricardoantolin.cabifystore.domain.executors.PostExecutionThread
import dev.ricardoantolin.cabifystore.domain.models.Product
import dev.ricardoantolin.cabifystore.domain.repositories.ProductsRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val postExecutionThread: PostExecutionThread
) {
    fun execute(): Flowable<List<Product>> = productsRepository.getProducts()
            .observeOn(postExecutionThread.getScheduler())
}