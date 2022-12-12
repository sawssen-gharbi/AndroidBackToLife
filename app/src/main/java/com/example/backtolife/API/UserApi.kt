package com.example.backtolife.API

import com.example.backtolife.LoginGoogleResponse
import com.example.backtolife.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface UserApi {

    @POST("user/login")
    fun login(@Body map : HashMap<String, String> ): Call<LoginResponse>
    @POST("user/loginGoogle")
    fun loginGoogle(@Body map : HashMap<String, String> ): Call<LoginGoogleResponse>

    @Multipart
    @POST("user/editLoginGoogle/{id}")
    fun editedLoginGoogle(@Path("id") id: String?, @PartMap map : HashMap<String, RequestBody>, @Part body: MultipartBody.Part?): Call<LoginGoogleResponse>

    @Multipart
    @POST("user/signup")
    fun signup(@PartMap map : HashMap<String, RequestBody>, @Part body: MultipartBody.Part): Call<SignupResponse>


    @POST("report/addReport")
    fun addReport(@Body map : HashMap<String, String> ,  @Query("idUser") idUser: String): Call<ReportResponse>
    @GET("report/getReport/{idUser}")
    fun getReport(@Path("idUser") id: String?) : Call<List<Report>>

    @POST("report/deleteReport/{id}")
    fun deleteReport(@Path("id") id: String?): Call<Report>

    @PUT("report/editReport/{id}")
    fun editReport(@Path("id") id: String?, @Body map : HashMap<String, String>): Call<Report>

    companion object {
        var BASE_URL = "http://192.168.1.12:7001/"
        fun create() : UserApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(UserApi::class.java)
        }
    }
}