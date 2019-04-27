package dev.ricardoantolin.cabifystore.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dev.ricardoantolin.cabifystore.domain.executors.PostExecutionThread
import dev.ricardoantolin.cabifystore.common.UIThread

@Module
abstract class ApplicationModule {

    /**
     * This companion object annotated as a module is necessary in order to provide dependencies
     * statically in case the wrapping module is an abstract class (to use binding)
     */
    @Module
    companion object {
        /**
        @Provides
        @JvmStatic
        fun provideSomething(): Something {
        return InstanceOfSomething
        }*/
    }

    @Binds
    abstract fun bindApplicationContext(application: Application): Context

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UIThread): PostExecutionThread
}