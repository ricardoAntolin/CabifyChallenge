package dev.ricardoantolin.cabifystorage.di.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import dev.ricardoantolin.cabifystorage.BuildConfig
import dev.ricardoantolin.cabifystorage.data.executors.ThreadExecutor
import dev.ricardoantolin.cabifystorage.data.providers.remote.ProductsRemoteProvider
import dev.ricardoantolin.cabifystorage.remote.executors.JobExecutor
import dev.ricardoantolin.cabifystorage.remote.services.products.RemoteProductsProvider

@Module
abstract class RemoteModule {
    /**
     * This companion object annotated as a module is necessary in order to provide dependencies
     * statically in case the wrapping module is an abstract class (to use binding)
     */
    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideProductsRemoteProvider(): ProductsRemoteProvider {
            return RemoteProductsProvider(BuildConfig.BASE_URL, BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor

    @Binds
    abstract fun bindProductsRemoteProvider(remoteProductsProvider: RemoteProductsProvider): ProductsRemoteProvider

}