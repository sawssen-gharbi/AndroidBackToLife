package com.example.backtolife.API

import com.example.backtolife.LoginFacebookResponse
import com.example.backtolife.LoginGoogleResponse
import com.example.backtolife.models.LoginResponse
import com.example.backtolife.models.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface UserApi {

    @POST("user/login")
    fun login(@Body map: HashMap<String, String>): Call<LoginResponse>

    @POST("user/loginGoogle")
    fun loginGoogle(@Body map: HashMap<String, String>): Call<LoginGoogleResponse>

    @Multipart
    @POST("user/editLoginGoogle/{id}")
    fun editedLoginGoogle(
        @Path("id") id: String?,
        @PartMap map: HashMap<String, RequestBody>,
        @Part body: MultipartBody.Part?,
    ): Call<LoginGoogleResponse>

    @Multipart
    @POST("user/signup")
    fun signupDoctor(
        @PartMap map: HashMap<String, RequestBody>,
        @Part body: MultipartBody.Part,
    ): Call<SignupResponse>

    @Multipart
    @POST("user/signup")
    fun signupPatient(@PartMap map: HashMap<String, RequestBody>): Call<SignupResponse>

    @POST("report/addReport")
    fun addReport(
        @Body map: HashMap<String, String>,
        @Query("idUser") idUser: String,
    ): Call<ReportResponse>

    @GET("report/getReport/{idUser}")
    fun getReport(@Path("idUser") id: String?): Call<List<Report>>

    @POST("report/deleteReport/{id}")
    fun deleteReport(@Path("id") id: String?): Call<Report>

    @PUT("report/editReport/{id}")
    fun editReport(@Path("id") id: String?, @Body map: HashMap<String, String>): Call<Report>

    @Multipart
    @POST("therapy")
    fun addOnce(
        @PartMap map: HashMap<String,
                RequestBody>,
        @Part body: MultipartBody.Part,
        @Query("idUser") idUser: String,
    ): Call<TherapyResponse>

    @GET("therapy")
    fun getTherapy(@Query("idUser") id: String?): Call<List<Therapy>>

    @GET("therapy")
    fun getAll(): Call<List<Therapy>>

    @GET("user/getUserByTherapy")
    fun getUser(@Query("idTherapy") id: String?): Call<List<patient>>

    @PUT("user/updateUser/{id}")
    fun editProfile(@Path("id") id: String?, @Body map: HashMap<String, String>): Call<User>

    @POST("user/forgotPassword")
    fun sendResetCode(@Body userReset: UserReset): Call<UserResetResponse>

    @PUT("user/editPassword")
    fun changePasswordReset(@Body passRes: UserResetPassword): Call<User>

    @POST("therapy/deleteOnce/{id}")
    fun deleteTherapy(@Path("id") id: String?): Call<Therapy>

    @Multipart
    @PATCH("user/updatePhoto/{id}")
    fun uploadPhoto(@Path("id") id: String?, @Part body: MultipartBody.Part): Call<User>

    @POST("reservation/send")
    fun sendInvitation(@Body body: HashMap<String, String>): Call<ServerResponse>

    @POST("reservation/accept")
    fun acceptInvitation(@Body body: HashMap<String, String>): Call<ServerResponse>

    @POST("reservation/refuse")
    fun refuseInvitation(@Body body: HashMap<String, String>): Call<ServerResponse>

    @GET("reservation")
    fun getAllR(@Query("doctor_id") doctorId: String): Call<List<ReservationItem>>

    @POST("reservation/deleteOnce/{id}")
    fun deleteReservation(@Path("id") id: String?): Call<ReservationItem>
    @GET("rss")
    suspend fun downloadArticles(@Query("page") pageNumber: Int? = 1): Response<PagedArticlesData>
    @Multipart
    @PUT("therapy/updateTherapy")
    fun updatePostSansImage(  @PartMap map: HashMap<String,
                                                            RequestBody>,

                            ): Call<TherapyResponse>
    @POST("user/loginFacebook")
    fun loginFacebook(@Body map : HashMap<String, String> ): Call<LoginFacebookResponse>


    companion object {
        var BASE_URL = "http://192.168.100.153:7001/"
        private val defaultInterceptor: OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                })
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .callTimeout(5, TimeUnit.SECONDS)
                .build()

        fun create(): UserApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(defaultInterceptor)
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(UserApi::class.java)
        }
    }
}