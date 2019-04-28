package dev.ricardoantolin.cabifystore.di.components

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dev.ricardoantolin.cabifystore.App
import dev.ricardoantolin.cabifystore.di.modules.*
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