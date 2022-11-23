package com.example.backtolife.API

import com.example.backtolife.LoginGoogleResponse
import com.example.backtolife.models.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {

    @POST("user/login")
    fun login(@Body map : HashMap<String, String> ): Call<LoginResponse>
    @POST("user/loginGoogle")
    fun loginGoogle(@Body map : HashMap<String, String> ): Call<LoginGoogleResponse>
    @POST("user/signup")
    fun signup(@Body map : HashMap<String, String> ): Call<SignupResponse>
    @POST("report/addReport")
    fun addReport(@Body map : HashMap<String, String> ): Call<ReportResponse>
    @GET("report/getReport/{id}")
    fun getReport(@Path("id") id: String?) : Call<List<Report>>

    companion object {
        var BASE_URL = "http://192.168.100.233:7001/"
        fun create() : UserApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(UserApi::class.java)
        }
    }
}