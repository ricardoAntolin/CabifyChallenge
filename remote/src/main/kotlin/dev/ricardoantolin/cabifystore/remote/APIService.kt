package dev.ricardoantolin.cabifystore.remote

import com.google.gson.Gson
import dev.ricardoantolin.cabifystore.remote.executors.JobExecutor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class APIService<T>
constructor(c: Class<T>, private val baseURL: String, private val debug: Boolean) {
    protected var service: T

    init {
        service = initApiService().create(c)
    }

    private fun initApiService(): Retrofit {

        val builder = OkHttpClient.Builder()
            .addInterceptor(getLoggingInterceptor())

        return Retrofit.Builder().baseUrl(baseURL)
            .callbackExecutor(JobExecutor())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(builder.build()).build()
    }


    private fun getLoggingInterceptor(): Interceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (debug)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }
}