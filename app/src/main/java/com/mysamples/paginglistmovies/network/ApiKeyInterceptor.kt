package com.mysamples.paginglistmovies.network

import okhttp3.Interceptor
import okhttp3.Response


class ApiKeyInterceptor : Interceptor {

    private val apiKey = "5d967c7c335764f39b1efbe9c5de9760"

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(NetworkModule.API_KEY_QUERY_PARAM, apiKey)
            .build()

        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}