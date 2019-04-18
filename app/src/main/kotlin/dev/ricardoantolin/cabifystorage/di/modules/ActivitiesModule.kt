package dev.ricardoantolin.cabifystorage.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.ricardoantolin.cabifystorage.MainActivity
import dev.ricardoantolin.cabifystorage.di.modules.fragments.MainFragmentsBuildersModule

@Module
abstract class ActivitiesModule {
    @ContributesAndroidInjector(modules = [(MainFragmentsBuildersModule::class)])
    abstract fun contributeMainActivityInjector(): MainActivity
}