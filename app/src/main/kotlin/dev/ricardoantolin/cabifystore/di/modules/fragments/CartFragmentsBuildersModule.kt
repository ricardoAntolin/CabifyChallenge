package dev.ricardoantolin.cabifystore.di.modules.fragments

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.ricardoantolin.cabifystore.scenes.cart.CartFragment

@Module
abstract class CartFragmentsBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeCartFragmentInjector(): CartFragment
}