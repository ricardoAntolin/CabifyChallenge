package dev.ricardoantolin.cabifystore.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.ricardoantolin.cabifystore.di.modules.fragments.CartFragmentsBuildersModule
import dev.ricardoantolin.cabifystore.di.modules.fragments.ProductDetailFragmentsBuildersModule
import dev.ricardoantolin.cabifystore.di.modules.fragments.ProductListFragmentsBuildersModule
import dev.ricardoantolin.cabifystore.scenes.cart.CartActivity
import dev.ricardoantolin.cabifystore.scenes.products.details.ProductDetailActivity
import dev.ricardoantolin.cabifystore.scenes.products.list.ProductListActivity
import dev.ricardoantolin.cabifystore.scenes.splash.SplashActivity

@Module
abstract class ActivitiesModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashActivityInjector(): SplashActivity

    @ContributesAndroidInjector(modules = [ProductListFragmentsBuildersModule::class])
    abstract fun contributeProductListActivityInjector(): ProductListActivity

    @ContributesAndroidInjector(modules = [ProductDetailFragmentsBuildersModule::class])
    abstract fun contributeProductDetailActivityInjector(): ProductDetailActivity

    @ContributesAndroidInjector(modules = [CartFragmentsBuildersModule::class])
    abstract fun contributeCartActivityInjector(): CartActivity
}