package dev.ricardoantolin.cabifystore.data.repositories

import dev.ricardoantolin.cabifystore.data.entities.CartEntity
import dev.ricardoantolin.cabifystore.data.entities.ProductEntity
import dev.ricardoantolin.cabifystore.data.providers.storage.CartStorageProvider
import dev.ricardoantolin.cabifystore.domain.models.Cart
import dev.ricardoantolin.cabifystore.domain.models.Product
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataCartRepositoryTest{
    lateinit var repositoryTest: DataCartRepository
    @Mock
    lateinit var storageProvider: CartStorageProvider

    @Before
    fun setUp() {
        repositoryTest = DataCartRepository(storageProvider)
    }

    @Test
    fun should_call_to_storage_provider_at_save() {
        val cartEntity = CartEntity(
            listOf(
                ProductEntity("VOUCHER", "Cabify Voucher", 5f),
                ProductEntity("TSHIRT", "Cabify T-Shirt", 20f),
                ProductEntity("MUG", "Cabify Coffee Mug", 7.5f)
            )
        )

        val cart = Cart(
            listOf(
                Product("VOUCHER", "Cabify Voucher", 5f),
                Product("TSHIRT", "Cabify T-Shirt", 20f),
                Product("MUG", "Cabify Coffee Mug", 7.5f)
            )
        )

        Mockito.`when`(storageProvider.save(cartEntity)).thenReturn(Completable.complete())

        repositoryTest.saveCart(cart)
            .test()
            .assertNoErrors()
            .assertComplete()

        Mockito.verify(storageProvider, Mockito.times(1)).save(cartEntity)
    }

    @Test
    fun should_call_to_storage_provider_at_get_cart() {
        val cartEntity = CartEntity(
            listOf(
                ProductEntity("VOUCHER", "Cabify Voucher", 5f),
                ProductEntity("TSHIRT", "Cabify T-Shirt", 20f),
                ProductEntity("MUG", "Cabify Coffee Mug", 7.5f)
            )
        )

        Mockito.`when`(storageProvider.findCart()).thenReturn(Flowable.just(cartEntity))

        val expectedCart = Cart(
            listOf(
                Product("VOUCHER", "Cabify Voucher", 5f),
                Product("TSHIRT", "Cabify T-Shirt", 20f),
                Product("MUG", "Cabify Coffee Mug", 7.5f)
            )
        )


        repositoryTest.getCart()
            .test()
            .assertNoErrors()
            .assertValue { it == expectedCart }
            .assertComplete()


        Mockito.verify(storageProvider, Mockito.times(1)).findCart()
    }
}