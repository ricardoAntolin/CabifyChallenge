package dev.ricardoantolin.cabifystore.domain.usecases

import dev.ricardoantolin.cabifystore.domain.executors.PostExecutionThread
import dev.ricardoantolin.cabifystore.domain.models.Cart
import dev.ricardoantolin.cabifystore.domain.models.Product
import dev.ricardoantolin.cabifystore.domain.repositories.CartRepository
import io.reactivex.Completable
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
class AddProductToCartUseCaseTest{
    private lateinit var addProductToCartUseCase: AddProductToCartUseCase

    @Mock
    private lateinit var cartRepository: CartRepository
    @Mock
    private lateinit var postExecutionThread: PostExecutionThread


    @Before
    fun setUp() {
        addProductToCartUseCase = AddProductToCartUseCase(
            cartRepository,
            postExecutionThread
        )
    }

    @Test
    fun should_call_repository_when_execute() {
        val cart = Cart(listOf())
        val product = Product("VOUCHER", "Cabify Voucher", 5f)

        val cartToSave = Cart(listOf(product))

        `when`(cartRepository.getCart()).thenReturn(Flowable.just(cart))
        `when`(cartRepository.saveCart(cartToSave)).thenReturn(Completable.complete())
        `when`(postExecutionThread.getScheduler()).thenReturn(Schedulers.io())

        addProductToCartUseCase.execute(product)
            .test()
            .assertNoErrors()

        Mockito.verify(cartRepository, Mockito.times(1)).getCart()
        Mockito.verify(cartRepository, Mockito.times(1)).saveCart(cartToSave)
    }
}