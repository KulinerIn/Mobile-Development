package com.example.kulinerin.api

import androidx.viewbinding.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val API_BASE_URL = "https://kulinerin-fv4qnoj45a-uc.a.run.app/"

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        }

    @Provides
    @Singleton
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}
