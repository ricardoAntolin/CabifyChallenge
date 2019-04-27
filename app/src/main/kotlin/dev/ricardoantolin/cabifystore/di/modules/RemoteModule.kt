package dev.ricardoantolin.cabifystore.di.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import dev.ricardoantolin.cabifystore.BuildConfig
import dev.ricardoantolin.cabifystore.data.executors.ThreadExecutor
import dev.ricardoantolin.cabifystore.data.providers.remote.ProductsRemoteProvider
import dev.ricardoantolin.cabifystore.remote.executors.JobExecutor
import dev.ricardoantolin.cabifystore.remote.services.products.RemoteProductsProvider

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
}