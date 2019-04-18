package dev.ricardoantolin.cabifystorage.di.components

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dev.ricardoantolin.cabifystorage.App
import dev.ricardoantolin.cabifystorage.data.di.DataModule
import dev.ricardoantolin.cabifystorage.di.modules.ActivitiesModule
import dev.ricardoantolin.cabifystorage.di.modules.ApplicationModule
import dev.ricardoantolin.cabifystorage.di.modules.ViewModelsModule
import dev.ricardoantolin.cabifystorage.domain.di.DomainModule
import dev.ricardoantolin.cabifystorage.remote.di.RemoteModule
import dev.ricardoantolin.cabifystorage.storage.di.StorageModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        ApplicationModule::class,
        ViewModelsModule::class,
        ActivitiesModule::class,
        DataModule::class,
        RemoteModule::class,
        StorageModule::class,
        DomainModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: App)
    fun context(): Context
}