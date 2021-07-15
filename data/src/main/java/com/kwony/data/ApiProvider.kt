package com.kwony.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiProvider {
    private val retrofit: Retrofit

    init {
        val gson = GsonBuilder().create()
        val apiClient = OkHttpClient.Builder().apply {
            this.readTimeout(30 * 1000.toLong(), TimeUnit.MILLISECONDS)
            this.writeTimeout(30 * 1000.toLong(), TimeUnit.MILLISECONDS)
            this.connectTimeout(30 * 1000.toLong(), TimeUnit.MILLISECONDS)
        }

        retrofit = Retrofit.Builder()
            .baseUrl("https://api.itbook.store/")
            .client(apiClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun <T> createApi(service: Class<T>): T = retrofit.create(service)
}