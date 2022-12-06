package com.example.myapplication.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// This works fine
object RetrofitClient {
    var retrofitInstance: Retrofit

    init {
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .build()

        retrofitInstance = Retrofit.Builder()
            .baseUrl("https://reqres.in")
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()
    }
}

val okHttpClient by lazy { OkHttpClient.Builder().build() }
val gsonConverterFactory: GsonConverterFactory by lazy {
    GsonConverterFactory.create()
}
val mHttpLoggingInterceptor by lazy {
    HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
}

class RetrofitClientTest {

    private val okHttpClientWithInterceptor: OkHttpClient = okHttpClient.newBuilder()
        .addInterceptor(mHttpLoggingInterceptor)
        .build()

    // If don't add http client everytime it works fine
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://reqres.in")
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClientWithInterceptor)
        .build()
}
