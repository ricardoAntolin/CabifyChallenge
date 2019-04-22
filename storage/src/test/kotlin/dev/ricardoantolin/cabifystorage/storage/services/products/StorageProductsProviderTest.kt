package dev.ricardoantolin.cabifystorage.storage.services.products

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import dev.ricardoantolin.cabifystorage.data.entities.ProductEntity
import io.realm.Realm
import io.realm.RealmConfiguration
import org.junit.After
import org.junit.Before
import org.junit.Test

class StorageProductsProviderTest {
    private lateinit var storageProductsProvider: StorageProductsProvider

    @Before
    fun setUp() {
        Realm.init(ApplicationProvider.getApplicationContext<Context>())
        RealmConfiguration.Builder()
            .inMemory()
            .build()
            .let { Realm.setDefaultConfiguration(it) }
        storageProductsProvider = StorageProductsProvider(RealmProductsService())
    }

    @After
    fun tearDown() {
        Realm.getDefaultInstance().close()
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
    }

    @Test
    fun must_get_all_products_stored_on_realm() {

    }
}