package dev.ricardoantolin.cabifystore.domain.usecases

import dev.ricardoantolin.cabifystore.domain.executors.PostExecutionThread
import dev.ricardoantolin.cabifystore.domain.repositories.ProductsRepository
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
class FetchProductsUseCaseTest {
    private lateinit var fetchProductsUseCase: FetchProductsUseCase

    @Mock
    private lateinit var productsRepository: ProductsRepository
    @Mock
    private lateinit var postExecutionThread: PostExecutionThread


    @Before
    fun setUp() {
        fetchProductsUseCase = FetchProductsUseCase(
            productsRepository,
            postExecutionThread
        )
    }

    @Test
    fun should_call_repository_when_execute() {
        `when`(productsRepository.fetchProducts()).thenReturn(Completable.complete())
        `when`(postExecutionThread.getScheduler()).thenReturn(Schedulers.io())

        fetchProductsUseCase.execute()
            .test()
            .assertNoErrors()

        Mockito.verify(productsRepository, Mockito.times(1)).fetchProducts()
    }
}