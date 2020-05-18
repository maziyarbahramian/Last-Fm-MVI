package com.maziyarbahramian.lastfm.api

import com.maziyarbahramian.lastfm.util.LiveDataCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyRetrofitBuilder {
    private const val BASE_URL = "http://ws.audioscrobbler.com/2.0/"

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(httpClient)
    }

    val apiService: ApiService by lazy {
        retrofitBuilder.build().create(ApiService::class.java)
    }

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor()
    }

    private val apiKeyInterceptor: Interceptor by lazy {
        object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                val url = request.url.newBuilder()
                    .addQueryParameter(name = "api_key", value = "53b11ed135fadc65d64fa8e092584e5c")
                    .build()
                request = request.newBuilder().url(url).build()
                return chain.proceed(request)
            }
        }
    }

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor.setLevel(level = HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(apiKeyInterceptor)
            .build()
    }
}