package com.example.backtolife

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.backtolife.API.UserApi
import com.example.studentchat.Interface.RealPathUtil
import com.facebook.AccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class MainActivityFacebook : AppCompatActivity() {
    lateinit var roleFacebook: CheckBox
    lateinit var certificatFacebook: RadioGroup
    lateinit var browseFacebook: Button
    lateinit var buttonNextFacebook: Button

    lateinit var usernameFacebook: TextView
    private lateinit var mSharedPref: SharedPreferences

    private lateinit var accessToken: AccessToken

    private lateinit var fullname: String

    private lateinit var email: String

    private lateinit var callFacebook: Call<LoginFacebookResponse>

    private lateinit var textViewUserFacebook : TextView

    private var pathF: String? = ""

    private var reqFileF: RequestBody? = null
    private var bodyF: MultipartBody.Part? = null

    //Image
    private lateinit var imageFacebook: ImageView
    private var multipartImageF: MultipartBody.Part? = null
    private var imageUF: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(/* requestCode = */ requestCode, /* resultCode = */
            resultCode, /* data = */
            data
        )
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE) {
            val rl = RealPathUtil()
            val pF: String? = data?.data?.let { this.let { it1 -> rl.getRealPath(it1, it) } }
            if (!pF.isNullOrEmpty()) {
                pathF = pF;
            }
            imageFacebook.setImageURI(data?.data)
            imageUF = Uri.parse(data?.dataString!!)
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMS_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mSharedPref.edit().putBoolean(IS_GRANTED_READ_IMAGES, true).apply()
            } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    val apiInterface = UserApi.create()
    val map: HashMap<String, RequestBody> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_facebook)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);


        imageFacebook = findViewById(R.id.imageViewTFacebook)
        roleFacebook = findViewById(R.id.checkBoxDoctorFacebook)
        certificatFacebook = findViewById(R.id.certificatGooglecertificatFacebook)
        browseFacebook = findViewById(R.id.buttonBrowseFacebook)
        usernameFacebook = findViewById(R.id.textViewUserFacebook)
        buttonNextFacebook = findViewById(R.id.buttonNextFacebook)

        roleFacebook.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                certificatFacebook.visibility = View.VISIBLE
                imageFacebook.visibility = View.VISIBLE
            } else {
                certificatFacebook.visibility = View.INVISIBLE
                imageFacebook.visibility = View.INVISIBLE

            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMS_REQUEST_CODE
            )
        }
        browseFacebook.setOnClickListener {
            startActivityForResult(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI
                ), PICK_IMAGE_CODE
            )
        }




        usernameFacebook = findViewById(R.id.textViewUserFacebook)
        textViewUserFacebook = findViewById(R.id.textViewUserFacebook)




        accessToken = AccessToken.getCurrentAccessToken()!!
        if (accessToken != null && !accessToken.isExpired) {
            var roleF = mSharedPref.getString(ROLE, "").toString()

            var certificateF = mSharedPref.getString(CERTIFICATE, "").toString()

            var nameF = mSharedPref.getString(FULLNAME, "").toString()

            textViewUserFacebook.text = nameF.toString()

            Log.e("ROLE", roleF.toString())

            Log.e("certificate", certificateF.toString())



            buttonNextFacebook.setOnClickListener {


                //image
                val fG= File(pathF)
                val reqFileF: RequestBody = fG.asRequestBody("image/jpeg".toMediaTypeOrNull())
                Log.e("file",reqFileF.toString())
                val bodyF: MultipartBody.Part=MultipartBody.Part.createFormData("certificate",fG.name,reqFileF)


                var rolee = "patient"

                if (roleFacebook.isChecked) {
                    roleF = roleFacebook.text.toString()
                    map["role"] = roleFacebook.text.toString().trim().toRequestBody("multipart/form-data".toMediaTypeOrNull())

                }


                CoroutineScope(Dispatchers.IO).launch {
                    callFacebook.enqueue(object : Callback<LoginFacebookResponse> {
                        override fun onResponse(
                            call: Call<LoginFacebookResponse>, response:
                            Response<LoginFacebookResponse>
                        ) {
                            val userFacebook = response.body()
                            Log.e("user", userFacebook.toString())
                            if (userFacebook != null) {
                                mSharedPref.edit().apply {
                                    putString(EMAIL, userFacebook.userFacebook.email)
                                    putString(FULLNAME, userFacebook.userFacebook.fullName)
                                    putString(ROLE, userFacebook.userFacebook.role)
                                    putString(CERTIFICATE, userFacebook.userFacebook.certificate)

                                }.apply()

                                if (userFacebook.userFacebook.role
                                        .equals("doctor")
                                ) {
                                    finish()
                                    val intent =
                                        Intent(this@MainActivityFacebook, MainDoctor::class.java)
                                    startActivity(intent)


                                } else {
                                    finish()
                                    val intent =
                                        Intent(
                                            this@MainActivityFacebook,
                                            MainActivityPatient::class.java
                                        )
                                    startActivity(intent)


                                }


                                var roleAfter = mSharedPref.getString(ROLE, "").toString()
                                Log.e("ROLE AFTER", roleAfter.toString())

                            }
                        }


                        override fun onFailure(
                            call: Call<LoginFacebookResponse>,
                            t: Throwable
                        ) {
                            Log.e("facebook: ", t.message.toString())
                            Log.e("fail", ":(")
                        }


                    })
                }


            }
        }
    }

}