package com.example.backtolife.API

import com.example.backtolife.models.LoginResponse
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

    @POST("user/signup")
    fun signup(@Body map : HashMap<String, String> ): Call<SignupResponse>
    @Multipart
    @POST("therapy")
    fun addOnce(@PartMap map: HashMap<String,
            RequestBody>, @Part body: MultipartBody.Part,
                @Query("idUser") idUser: String
    ): Call<TherapyResponse>

    @GET("therapy")
    fun getTherapy(@Query("idUser") id: String?) : Call<List<Therapy>>
    @GET("user/getUserByTherapy")
    fun getUser(@Query("idTherapy") id: String?) : Call<List<patient>>
    @PUT("user/updateUser/{id}")
    fun editProfile(@Path("id") id: String?, @Body map: HashMap<String, String>): Call<User>
    @POST("user/forgotPassword")
    fun sendResetCode(@Body userReset: UserReset): Call<UserResetResponse>
    @PUT("user/editPassword")
    fun changePasswordReset(@Body passRes: UserResetPassword): Call<User>
    @POST("therapy/deleteOnce/{id}")
    fun deleteTherapy(@Path("id") id: String?): Call<Therapy>
    @Multipart
    @PATCH("user//updatePhoto")
    fun uploadPhoto( @Part body: MultipartBody.Part): Call<User>





    companion object {
        var BASE_URL = "http://192.168.1.16:7001/"
        fun create() : UserApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(UserApi::class.java)
        }
    }
}