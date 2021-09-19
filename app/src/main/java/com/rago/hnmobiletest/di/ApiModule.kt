package com.rago.hnmobiletest.di

import android.content.Context
import com.rago.hnmobiletest.data.network.AlgoliaQuery
import com.rago.hnmobiletest.utils.isInternetAvailable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "https://hn.algolia.com/api/v1/"


    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)
        return OkHttpClient
            .Builder()
            .cache(myCache)
            .addNetworkInterceptor { chain ->
                val originalResponse = chain.proceed(chain.request())
                val cacheControl = originalResponse.header("Cache-Control")
                return@addNetworkInterceptor if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains(
                        "no-cache"
                    ) ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")
                ) {
                    originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + 5000)
                        .build()
                } else {
                    originalResponse
                }
            }
            .addInterceptor { chain ->
                var request = chain.request()
                if (!isInternetAvailable(context)) {
                    request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached")
                        .build()
                }
                return@addInterceptor chain.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    fun provideAlgoliaQuery(retrofit: Retrofit): AlgoliaQuery =
        retrofit.create(AlgoliaQuery::class.java)
}