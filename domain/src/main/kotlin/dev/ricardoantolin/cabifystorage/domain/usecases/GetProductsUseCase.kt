package dev.ricardoantolin.cabifystorage.domain.usecases

import dev.ricardoantolin.cabifystorage.domain.executors.PostExecutionThread
import dev.ricardoantolin.cabifystorage.domain.models.Product
import dev.ricardoantolin.cabifystorage.domain.repositories.ProductsRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val postExecutionThread: PostExecutionThread
) {
    fun execute(): Flowable<List<Product>> = productsRepository.getProducts()
            .observeOn(postExecutionThread.getScheduler())
}