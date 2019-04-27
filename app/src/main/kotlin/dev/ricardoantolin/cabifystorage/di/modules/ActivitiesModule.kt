package dev.ricardoantolin.cabifystorage.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.ricardoantolin.cabifystorage.di.modules.fragments.MainFragmentsBuildersModule
import dev.ricardoantolin.cabifystorage.escenes.splash.SplashActivity

@Module
abstract class ActivitiesModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashActivityInjector(): SplashActivity
}