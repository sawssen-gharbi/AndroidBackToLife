package com.example.backtolife.API

import com.example.backtolife.models.LoginResponse
import com.example.backtolife.models.ReportResponse
import com.example.backtolife.models.SignupResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("user/login")
    fun login(@Body map : HashMap<String, String> ): Call<LoginResponse>
    @POST("user/signup")
    fun signup(@Body map : HashMap<String, String> ): Call<SignupResponse>
    @POST("report/addReport")
    fun addReport(@Body map : HashMap<String, String> ): Call<ReportResponse>


    companion object {
        var BASE_URL = "http://192.168.1.116:7001/"
        fun create() : UserApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(UserApi::class.java)
        }
    }
}