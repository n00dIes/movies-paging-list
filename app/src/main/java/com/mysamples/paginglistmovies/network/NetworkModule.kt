package com.mysamples.paginglistmovies.network

import com.google.gson.GsonBuilder
import com.mysamples.paginglistmovies.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NetworkModule {

    private const val HOST_BASE_URL = "https://api.themoviedb.org"
    private const val VERSION = 3
    private const val IMAGE_SIZE = 200

    const val API_BASE_URL = "$HOST_BASE_URL/$VERSION/"
    const val IMG_BASE_URL = "https://image.tmdb.org/t/p/w$IMAGE_SIZE"
    const val API_KEY_QUERY_PARAM = "api_key"

    private const val CONNECTION_TIMEOUT_SEC = 10L
    private const val RW_TIMEOUT = 30L


    fun tvShowsApiService(): TvShowApiInteraface? {
        val httpClient =
            provideLoggingCapableHttpClient(provideLoggingInterceptor(), ApiKeyInterceptor())
        val converterFactory = provideConverterFactory()
        val rxJavaCallAdapterFactory = provideRxJavaCallAdapterFactory()

        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(rxJavaCallAdapterFactory)
            .client(httpClient)
            .build()
            .create(TvShowApiInteraface::class.java)
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }


    private fun provideLoggingCapableHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        apiKeyInterceptor: ApiKeyInterceptor
    ) =
        OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(RW_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(RW_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()


    private fun provideConverterFactory() = GsonConverterFactory.create(GsonBuilder().create())

    private fun provideRxJavaCallAdapterFactory() = RxJava2CallAdapterFactory.create()
}