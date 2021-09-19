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

//Modulo para el cliente retrofit para la conexion al API
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    // URL del API
    private const val BASE_URL = "https://hn.algolia.com/api/v1/"

    // Provider del cliente Http
    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {

        //Configuracion del tamano del cache
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)
        return OkHttpClient
            .Builder()
            //configurando cache para el cliente Http
            .cache(myCache)
            .addNetworkInterceptor { chain ->
                val originalResponse = chain.proceed(chain.request())
                //Agregamos los encabezados de la respuesta online del cliente http
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
                //En caso de no tener red disponible, se crea un nueva respuesta con la que se almaceno
                // en cache cuando se logro realizar una conexion exitosa
                if (!isInternetAvailable(context)) {
                    request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached")
                        .build()
                }
                return@addInterceptor chain.proceed(request)
            }
            .build()
    }

    //Provider de Retrofit Singlenton
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    //Provider de AlgoliaQuery para la peticion http get al API
    @Provides
    fun provideAlgoliaQuery(retrofit: Retrofit): AlgoliaQuery =
        retrofit.create(AlgoliaQuery::class.java)
}