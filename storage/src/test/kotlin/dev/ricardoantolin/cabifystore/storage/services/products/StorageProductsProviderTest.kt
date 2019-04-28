package dev.ricardoantolin.cabifystore.storage.services.products

import dev.ricardoantolin.cabifystore.data.entities.ProductEntity
import dev.ricardoantolin.cabifystore.storage.entities.RLCartEntity
import dev.ricardoantolin.cabifystore.storage.entities.RLProductEntity
import dev.ricardoantolin.cabifystore.storage.extensions.completableTransaction
import dev.ricardoantolin.cabifystore.storage.services.RealmTest
import io.reactivex.Flowable
import io.realm.RealmObjectSchema
import io.realm.RealmQuery
import io.realm.RealmResults
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.powermock.api.mockito.PowerMockito.`when`
import org.powermock.api.mockito.PowerMockito.mockStatic


class StorageProductsProviderTest: RealmTest() {
    private lateinit var storageProductsProvider: StorageProductsProvider

    @Before
    override fun setUp() {
        super.setUp()
        storageProductsProvider = StorageProductsProvider()
    }

    @Test
    fun must_save_all_products_on_realm() {
        val products = listOf(
            ProductEntity("VOUCHER", "Cabify Voucher", 5f),
            ProductEntity("TSHIRT", "Cabify T-Shirt", 20f),
            ProductEntity("MUG", "Cabify Coffee Mug", 7.5f)
        )

        storageProductsProvider.saveAll(products)
            .test()
            .assertNoErrors()
            .assertComplete()

        val expectedProducts = listOf(
            RLProductEntity("VOUCHER", "Cabify Voucher", 5f),
            RLProductEntity("TSHIRT", "Cabify T-Shirt", 20f),
            RLProductEntity("MUG", "Cabify Coffee Mug", 7.5f)
        )

        verify(mockRealm, times(1)).completableTransaction{ mockRealm.copyToRealmOrUpdate(expectedProducts) }
    }

    @Test
    fun must_get_all_products_stored_on_realm() {
        val products = listOf(
            RLProductEntity("VOUCHER", "Cabify Voucher", 5f),
            RLProductEntity("TSHIRT", "Cabify T-Shirt", 20f),
            RLProductEntity("MUG", "Cabify Coffee Mug", 7.5f)
        )

        val mockRealmQuery: RealmQuery<RLProductEntity> = mockRealmQuery()
        `when`(mockRealm.where(RLProductEntity::class.java)).thenReturn(mockRealmQuery)
        mockStatic(RealmResults::class.java)
        val mockRealmResults: RealmResults<RLProductEntity> = mockRealmResults()
        `when`(mockRealmQuery.findAllAsync()).thenReturn(mockRealmResults)
        `when`(mockRealmResults.asFlowable()).thenReturn(Flowable.just(mockRealmResults))
        `when`(mockRealmResults.isLoaded()).thenReturn(true)
        `when`(mockRealm.copyFromRealm(mockRealmResults)).thenReturn(products)


        val expectedProducts = listOf(
            ProductEntity("VOUCHER", "Cabify Voucher", 5f),
            ProductEntity("TSHIRT", "Cabify T-Shirt", 20f),
            ProductEntity("MUG", "Cabify Coffee Mug", 7.5f)
        )

        storageProductsProvider.findAll()
            .test()
            .assertNoErrors()
            .assertValue {
                it == expectedProducts
            }
    }
}

