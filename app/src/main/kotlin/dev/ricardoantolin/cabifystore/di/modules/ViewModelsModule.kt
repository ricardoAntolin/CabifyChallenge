package dev.ricardoantolin.cabifystore.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.ricardoantolin.cabifystore.common.ViewModelFactory
import dev.ricardoantolin.cabifystore.di.ViewModelKey
import dev.ricardoantolin.cabifystore.scenes.cart.CartViewModel
import dev.ricardoantolin.cabifystore.scenes.products.details.ProductDetailViewModel
import dev.ricardoantolin.cabifystore.scenes.products.list.ProductListViewModel
import dev.ricardoantolin.cabifystore.scenes.splash.SplashViewModel

@Module
abstract class ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductListViewModel::class)
    abstract fun bindProductListModel(viewModel: ProductListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailViewModel::class)
    abstract fun bindProductDetailModel(viewModel: ProductDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    abstract fun bindCartModel(viewModel: CartViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}