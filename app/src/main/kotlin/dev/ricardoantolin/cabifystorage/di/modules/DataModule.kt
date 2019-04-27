package dev.ricardoantolin.cabifystorage.di.modules

import dagger.Binds
import dagger.Module
import dev.ricardoantolin.cabifystorage.data.repositories.DataProductsRepository
import dev.ricardoantolin.cabifystorage.domain.repositories.ProductsRepository

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

}