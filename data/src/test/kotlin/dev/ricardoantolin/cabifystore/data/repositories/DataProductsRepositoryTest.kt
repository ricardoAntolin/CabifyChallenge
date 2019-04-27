package dev.ricardoantolin.cabifystore.data.repositories

import dev.ricardoantolin.cabifystore.data.entities.ProductEntity
import dev.ricardoantolin.cabifystore.data.providers.remote.ProductsRemoteProvider
import dev.ricardoantolin.cabifystore.data.providers.storage.ProductsStorageProvider
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import dev.ricardoantolin.cabifystore.data.utils.any
import dev.ricardoantolin.cabifystore.domain.models.Product
import io.reactivex.Flowable
import org.mockito.Mockito.*

@RunWith(MockitoJUnitRunner::class)
class DataProductsRepositoryTest {
    lateinit var repositoryTest: DataProductsRepository
    @Mock
    lateinit var storageProvider: ProductsStorageProvider
    @Mock
    lateinit var remoteProvider: ProductsRemoteProvider

    @Before
    fun setUp() {
        repositoryTest = DataProductsRepository(storageProvider, remoteProvider)
    }

    @Test
    fun should_call_to_remote_and_storage_providers_at_update() {
        val products = listOf(
            ProductEntity("VOUCHER", "Cabify Voucher", 5f),
            ProductEntity("TSHIRT", "Cabify T-Shirt", 20f),
            ProductEntity("MUG", "Cabify Coffee Mug", 7.5f)
        )

        `when`(remoteProvider.fetchProducts()).thenReturn(Single.just(products))
        `when`(storageProvider.saveAll(products)).thenReturn(Completable.complete())

        repositoryTest.updateProducts()
            .test()
            .assertNoErrors()
            .assertComplete()

        verify(remoteProvider, times(1)).fetchProducts()
        verify(storageProvider, times(1)).saveAll(products)
    }

    @Test
    fun should_not_call_to_storage_provider_when_remote_response_error_at_update() {
        val error = Throwable()
        `when`(remoteProvider.fetchProducts()).thenReturn(Single.error(error))

        repositoryTest.updateProducts()
            .test()
            .assertError(error)

        verify(remoteProvider, times(1)).fetchProducts()
        verify(storageProvider, never()).saveAll(any())
    }

    @Test
    fun should_call_to_storage_provider_at_get_all() {
        val products = listOf(
            ProductEntity("VOUCHER", "Cabify Voucher", 5f),
            ProductEntity("TSHIRT", "Cabify T-Shirt", 20f),
            ProductEntity("MUG", "Cabify Coffee Mug", 7.5f)
        )

        `when`(storageProvider.findAll()).thenReturn(Flowable.just(products))

        val expectedProducts = listOf(
            Product("VOUCHER", "Cabify Voucher", 5f),
            Product("TSHIRT", "Cabify T-Shirt", 20f),
            Product("MUG", "Cabify Coffee Mug", 7.5f)
        )

        repositoryTest.getProducts()
            .test()
            .assertNoErrors()
            .assertValue { it == expectedProducts }
            .assertComplete()


        verify(storageProvider, times(1)).findAll()
    }
}