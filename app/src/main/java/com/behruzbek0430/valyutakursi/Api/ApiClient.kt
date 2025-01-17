package com.behruzbek0430.valyutakursi.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val BASE_URL = "https://cbu.uz/uzc/arkhiv-kursov-valyut/"
    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getApiService(): ApiService{
        return getRetrofit().create(ApiService::class.java)
    }

}