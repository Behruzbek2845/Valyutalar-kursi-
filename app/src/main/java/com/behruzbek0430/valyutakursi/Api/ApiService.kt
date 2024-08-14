package com.behruzbek0430.valyutakursi.Api

import com.behruzbek0430.valyutakursi.models.MyMoney
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("json/")
    fun getNotes(): Call<List<MyMoney>>


}