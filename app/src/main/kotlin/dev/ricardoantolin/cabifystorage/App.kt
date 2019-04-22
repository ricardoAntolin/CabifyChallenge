package dev.ricardoantolin.cabifystorage

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.annotation.VisibleForTesting
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dev.ricardoantolin.cabifystorage.di.AppInjector
import dev.ricardoantolin.cabifystorage.di.components.AppComponent
import io.realm.Realm
import javax.inject.Inject

class App: Application(), HasActivityInjector, HasServiceInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>
    override fun activityInjector(): DispatchingAndroidInjector<Activity> = activityInjector
    override fun serviceInjector(): AndroidInjector<Service> = serviceInjector

    var appComponent: AppComponent = AppInjector.init(this)
        @VisibleForTesting
        set

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        Realm.init(this)
    }
}