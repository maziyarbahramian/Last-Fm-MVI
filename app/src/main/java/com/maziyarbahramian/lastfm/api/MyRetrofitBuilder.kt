package com.maziyarbahramian.lastfm.api

import com.maziyarbahramian.lastfm.util.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object MyRetrofitBuilder {
    val BASE_URL = "http://ws.audioscrobbler.com/2.0/"

    val interceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor()
    }

    val httpClient: OkHttpClient by lazy {
        OkHttpClient().newBuilder()
            .addInterceptor(interceptor.setLevel(level = HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(httpClient)
    }

    val apiService: ApiService by lazy {
        retrofitBuilder.build().create(ApiService::class.java)
    }
}