package dev.ricardoantolin.cabifystore

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.annotation.VisibleForTesting
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dev.ricardoantolin.cabifystore.di.AppInjector
import dev.ricardoantolin.cabifystore.di.components.AppComponent
import io.realm.Realm
import io.realm.RealmConfiguration
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
        initRealm()
    }

    private fun initRealm() {
        Realm.init(this)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        )
    }
}