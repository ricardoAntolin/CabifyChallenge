package dev.ricardoantolin.cabifystore.storage.services.products

import dev.ricardoantolin.cabifystore.data.entities.ProductEntity
import dev.ricardoantolin.cabifystore.storage.entities.RLProductEntity
import dev.ricardoantolin.cabifystore.storage.extensions.completableTransaction
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmResults
import io.realm.log.RealmLog
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor
import org.powermock.modules.junit4.rule.PowerMockRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import io.realm.RealmObject
import io.realm.RealmQuery
import org.powermock.api.mockito.PowerMockito.*


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest(Realm::class, RealmLog::class, RealmQuery::class, RealmResults::class)
class StorageProductsProviderTest {
    private lateinit var storageProductsProvider: StorageProductsProvider
    @get:Rule
    val rule = PowerMockRule()
    private lateinit var mockRealm: Realm

    @Before
    fun setUp() {
        mockStatic(RealmLog::class.java)
        mockStatic(Realm::class.java)
        mockRealm = PowerMockito.mock(Realm::class.java)
        `when`(Realm.getDefaultInstance()).thenReturn(mockRealm)

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

@Suppress("UNCHECKED_CAST")
fun <T : RealmObject> mockRealmQuery(): RealmQuery<T> {
    return mock(RealmQuery::class.java) as RealmQuery<T>
}

@Suppress("UNCHECKED_CAST")
fun <T : RealmObject> mockRealmResults(): RealmResults<T> {
    return mock(RealmResults::class.java) as RealmResults<T>
}
