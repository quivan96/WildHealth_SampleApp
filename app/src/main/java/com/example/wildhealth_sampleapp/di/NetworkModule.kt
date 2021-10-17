package com.example.wildhealth_sampleapp.di

import com.example.wildhealth_sampleapp.BuildConfig
import com.example.wildhealth_sampleapp.utils.API_HOST_HEADER_NAME
import com.example.wildhealth_sampleapp.utils.API_KEY_HEADER_NAME
import com.example.wildhealth_sampleapp.utils.BASE_URL
import com.example.wildhealth_sampleapp.utils.TIME_OUT
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesMoshi() = Moshi.Builder().build()

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
                .setLevel(HttpLoggingInterceptor.Level.BODY)

        val networkInterceptor = Interceptor { chain ->
            val requestBuilder =
                chain.request().newBuilder().header(API_KEY_HEADER_NAME, BuildConfig.API_KEY)
                    .header(API_HOST_HEADER_NAME, BuildConfig.API_HOST)
                    .method(chain.request().method, chain.request().body)
                    .build()
            chain.proceed(requestBuilder)
        }

        val okHttpBuilder =
            OkHttpClient.Builder().readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(networkInterceptor)

        return okHttpBuilder.build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(moshi: Moshi, client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
}