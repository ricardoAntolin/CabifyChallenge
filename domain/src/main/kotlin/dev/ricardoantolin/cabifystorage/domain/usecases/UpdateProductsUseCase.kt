package dev.ricardoantolin.cabifystorage.domain.usecases

import dev.ricardoantolin.cabifystorage.domain.executors.PostExecutionThread
import dev.ricardoantolin.cabifystorage.domain.repositories.ProductsRepository
import io.reactivex.Completable
import javax.inject.Inject

class UpdateProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val postExecutionThread: PostExecutionThread
) {
    fun execute(): Completable = productsRepository.updateProducts()
            .observeOn(postExecutionThread.getScheduler())
}