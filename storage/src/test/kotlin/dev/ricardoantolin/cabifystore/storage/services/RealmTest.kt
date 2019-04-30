package dev.ricardoantolin.cabifystore.storage.services

import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.log.RealmLog
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor
import org.powermock.modules.junit4.rule.PowerMockRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest(Realm::class, RealmLog::class, RealmQuery::class, RealmResults::class)
abstract class RealmTest{
    @get:Rule
    val rule = PowerMockRule()
    protected lateinit var mockRealm: Realm

    @Before
    open fun setUp() {
        PowerMockito.mockStatic(RealmLog::class.java)
        PowerMockito.mockStatic(Realm::class.java)
        mockRealm = PowerMockito.mock(Realm::class.java)
        PowerMockito.`when`(Realm.getDefaultInstance()).thenReturn(mockRealm)
    }

    @After
    open fun tearDown() {
        Realm.getDefaultInstance().close()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : RealmObject> mockRealmQuery(): RealmQuery<T> {
        return PowerMockito.mock(RealmQuery::class.java) as RealmQuery<T>
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : RealmObject> mockRealmResults(): RealmResults<T> {
        return PowerMockito.mock(RealmResults::class.java) as RealmResults<T>
    }
}


