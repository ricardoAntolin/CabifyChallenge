package dev.ricardoantolin.cabifystorage.domain.usecases

import dev.ricardoantolin.cabifystorage.domain.executors.PostExecutionThread
import dev.ricardoantolin.cabifystorage.domain.repositories.ProductsRepository
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UpdateProductsUseCaseTest {
    private lateinit var updateProductsUseCase: UpdateProductsUseCase

    @Mock
    private lateinit var productsRepository: ProductsRepository
    @Mock
    private lateinit var postExecutionThread: PostExecutionThread


    @Before
    fun setUp() {
        updateProductsUseCase = UpdateProductsUseCase(
            productsRepository,
            postExecutionThread
        )
    }

    @Test
    fun should_call_repository_when_execute() {
        `when`(productsRepository.updateProducts()).thenReturn(Completable.complete())
        `when`(postExecutionThread.getScheduler()).thenReturn(Schedulers.io())

        updateProductsUseCase.execute()
            .test()
            .assertNoErrors()

        Mockito.verify(productsRepository, Mockito.times(1)).updateProducts()
    }
}