package dev.ricardoantolin.cabifystore.di.modules.fragments

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.ricardoantolin.cabifystore.scenes.products.details.ProductDetailFragment

@Module
abstract class ProductDetailFragmentsBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeProductDetailFragmentInjector(): ProductDetailFragment
}