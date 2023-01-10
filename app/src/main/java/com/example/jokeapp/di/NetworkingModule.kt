package com.example.jokeapp.di

import android.content.Context
import com.example.jokeapp.BuildConfig
import com.example.jokeapp.data.remote.NetworkService
import com.example.jokeapp.data.repository.JokesRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Singleton
    @Provides
    fun createOkHttpClient(@ApplicationContext applicationContext: Context): OkHttpClient {

        val builder = OkHttpClient().newBuilder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(logging)
        }
        return builder
            .cache(Cache(applicationContext.cacheDir, CASH_SIZE))
            .readTimeout(NETWORKING_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(NETWORKING_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    @Named("retrofit")
    fun createRetrofit(okHttpClient: OkHttpClient, mosh: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(mosh))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideLoginApi(@Named("retrofit") retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkRepository(api: NetworkService): JokesRepositoryImpl {
        return JokesRepositoryImpl(api)
    }

    /**
     * Constants to create okhttp client
     * */
    private const val CASH_SIZE: Long = 10 * 1024 * 1024
    private const val NETWORKING_TIMEOUT: Long = 1
}