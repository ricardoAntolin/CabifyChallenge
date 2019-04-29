package dev.ricardoantolin.cabifystore.di.modules

import dagger.Binds
import dagger.Module
import dev.ricardoantolin.cabifystore.data.repositories.DataCartRepository
import dev.ricardoantolin.cabifystore.data.repositories.DataProductsRepository
import dev.ricardoantolin.cabifystore.domain.repositories.CartRepository
import dev.ricardoantolin.cabifystore.domain.repositories.ProductsRepository

@Module
abstract class DataModule {
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
    abstract fun bindProductsRepository(productsRepository: DataProductsRepository): ProductsRepository

    @Binds
    abstract fun bindCartRepository(cartRepository: DataCartRepository): CartRepository

}