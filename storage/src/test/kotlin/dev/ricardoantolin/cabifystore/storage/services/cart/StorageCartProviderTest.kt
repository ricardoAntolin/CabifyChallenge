package dev.ricardoantolin.cabifystore.storage.services.cart

import dev.ricardoantolin.cabifystore.data.entities.CartEntity
import dev.ricardoantolin.cabifystore.data.entities.ProductEntity
import dev.ricardoantolin.cabifystore.storage.entities.RLCartEntity
import dev.ricardoantolin.cabifystore.storage.entities.RLProductEntity
import dev.ricardoantolin.cabifystore.storage.extensions.completableTransaction
import dev.ricardoantolin.cabifystore.storage.services.RealmTest
import io.reactivex.Flowable
import io.realm.RealmList
import io.realm.RealmObjectSchema
import io.realm.RealmQuery
import io.realm.RealmSchema
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.api.mockito.PowerMockito.mock


class StorageCartProviderTest : RealmTest() {
    private lateinit var storageCartProvider: StorageCartProvider

    @Before
    override fun setUp() {
        super.setUp()
        storageCartProvider = StorageCartProvider()
    }

    @Test
    fun must_save_cart_on_realm() {
        val cart = CartEntity(
            listOf(
                ProductEntity("VOUCHER", "Cabify Voucher", 5f),
                ProductEntity("TSHIRT", "Cabify T-Shirt", 20f),
                ProductEntity("MUG", "Cabify Coffee Mug", 7.5f)
            )
        )

        storageCartProvider.save(cart)
            .test()
            .assertNoErrors()
            .assertComplete()

        val expectedCart = RLCartEntity(
            "cart-id",
            RealmList(
                RLProductEntity("VOUCHER", "Cabify Voucher", 5f),
                RLProductEntity("TSHIRT", "Cabify T-Shirt", 20f),
                RLProductEntity("MUG", "Cabify Coffee Mug", 7.5f)
            )
        )

        Mockito.verify(mockRealm, Mockito.times(1))
            .completableTransaction { mockRealm.copyToRealmOrUpdate(expectedCart) }
    }

    @Test
    fun must_get_cart_stored_on_realm() {
        val realmSchemaMock = mock(RealmSchema::class.java)
        val schemaMock = mock(RealmObjectSchema::class.java)
        PowerMockito.`when`(mockRealm.schema).thenReturn(realmSchemaMock)
        PowerMockito.`when`(realmSchemaMock.get(RLCartEntity::class.java.simpleName)).thenReturn(schemaMock)
        PowerMockito.`when`(schemaMock.hasPrimaryKey()).thenReturn(true)
        PowerMockito.`when`(schemaMock.primaryKey).thenReturn("cartId")

        val cart: RLCartEntity = RLCartEntity(
            "cart-id",
            RealmList(
                RLProductEntity("VOUCHER", "Cabify Voucher", 5f),
                RLProductEntity("TSHIRT", "Cabify T-Shirt", 20f),
                RLProductEntity("MUG", "Cabify Coffee Mug", 7.5f)
            )
        )
        val mockRealmObject = mock(RLCartEntity::class.java)

        val mockRealmQuery: RealmQuery<RLCartEntity> = mockRealmQuery()
        PowerMockito.`when`(mockRealm.where(RLCartEntity::class.java)).thenReturn(mockRealmQuery)
        PowerMockito.`when`(mockRealmQuery.equalTo("cartId", "cart.id")).thenReturn(mockRealmQuery)
        PowerMockito.`when`(mockRealmQuery.findFirstAsync()).thenReturn(mockRealmObject)
        PowerMockito.`when`(mockRealmObject.asFlowable<RLCartEntity>()).thenReturn(Flowable.just(cart))

        val expectedCart = CartEntity(
            listOf(
                ProductEntity("VOUCHER", "Cabify Voucher", 5f),
                ProductEntity("TSHIRT", "Cabify T-Shirt", 20f),
                ProductEntity("MUG", "Cabify Coffee Mug", 7.5f)
            )
        )

        storageCartProvider.findCart()
            .test()
            .assertNoErrors()
            .assertValue {
                it == expectedCart
            }
    }
}