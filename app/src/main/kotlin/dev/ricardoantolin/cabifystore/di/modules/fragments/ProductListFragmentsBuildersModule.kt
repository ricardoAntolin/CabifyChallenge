package dev.ricardoantolin.cabifystore.di.modules.fragments

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.ricardoantolin.cabifystore.scenes.products.list.ProductListFragment

@Module
abstract class ProductListFragmentsBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeProductDetailFragmentInjector(): ProductListFragment
}