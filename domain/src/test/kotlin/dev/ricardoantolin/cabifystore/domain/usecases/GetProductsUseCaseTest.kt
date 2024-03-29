package dev.ricardoantolin.cabifystore.domain.usecases

import dev.ricardoantolin.cabifystore.domain.executors.PostExecutionThread
import dev.ricardoantolin.cabifystore.domain.repositories.ProductsRepository
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetProductsUseCaseTest {

    private lateinit var getProductsUseCase: GetProductsUseCase

    @Mock
    private lateinit var productsRepository: ProductsRepository
    @Mock
    private lateinit var postExecutionThread: PostExecutionThread


    @Before
    fun setUp() {
        getProductsUseCase = GetProductsUseCase(
            productsRepository,
            postExecutionThread
        )
    }

    @Test
    fun should_call_repository_when_execute() {
        Mockito.`when`(productsRepository.getProducts()).thenReturn(Flowable.just(listOf()))
        Mockito.`when`(postExecutionThread.getScheduler()).thenReturn(Schedulers.io())

        getProductsUseCase.execute()
            .test()
            .assertNoErrors()
            .assertValue { it.isEmpty() }
            .assertComplete()

        verify(productsRepository, times(1)).getProducts()
    }
}