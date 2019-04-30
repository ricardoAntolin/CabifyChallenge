package dev.ricardoantolin.cabifystore.di.modules

import dagger.Binds
import dagger.Module
import dev.ricardoantolin.cabifystore.data.providers.storage.CartStorageProvider
import dev.ricardoantolin.cabifystore.data.providers.storage.ProductsStorageProvider
import dev.ricardoantolin.cabifystore.storage.services.cart.StorageCartProvider
import dev.ricardoantolin.cabifystore.storage.services.products.StorageProductsProvider


@Module
abstract class StorageModule {

    /**
     * This companion object annotated as a module is necessary in order to provide dependencies
     * statically in case the wrapping module is an abstract class (to use binding)
     */
    @Module
    companion object {
        /**
        @Provides
        @JvmStatic
        fun provideSomething(): Something {
        return InstanceOfSomething
        }*/
    }

    @Binds
    abstract fun bindProductsStorageProvider(storageProductsProvider: StorageProductsProvider): ProductsStorageProvider

    @Binds
    abstract fun bindCartStorageProvider(storageCartProvider: StorageCartProvider): CartStorageProvider

}