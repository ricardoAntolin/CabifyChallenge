package dev.ricardoantolin.cabifystore.domain.usecases

import dev.ricardoantolin.cabifystore.domain.executors.PostExecutionThread
import dev.ricardoantolin.cabifystore.domain.models.Cart
import dev.ricardoantolin.cabifystore.domain.repositories.CartRepository
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCartUseCaseTest{

    private lateinit var getCartUseCase: GetCartUseCase

    @Mock
    private lateinit var cartRepository: CartRepository
    @Mock
    private lateinit var postExecutionThread: PostExecutionThread


    @Before
    fun setUp() {
        getCartUseCase = GetCartUseCase(
            cartRepository,
            postExecutionThread
        )
    }

    @Test
    fun should_call_repository_when_execute() {
        val cart = Cart(listOf())

        `when`(cartRepository.getCart()).thenReturn(Flowable.just(cart))
        `when`(postExecutionThread.getScheduler()).thenReturn(Schedulers.io())

        getCartUseCase.execute()
            .test()
            .assertNoErrors()
            .assertValue { it == cart }
            .assertComplete()

        Mockito.verify(cartRepository, Mockito.times(1)).getCart()
    }
}