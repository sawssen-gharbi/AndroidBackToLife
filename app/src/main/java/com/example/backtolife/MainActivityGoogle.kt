package com.example.backtolife

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import com.example.backtolife.API.UserApi
import com.example.backtolife.models.User
import com.example.studentchat.Interface.RealPathUtil


import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MainActivityGoogle : AppCompatActivity() {

    lateinit var roleGoogle: CheckBox
    lateinit var certificatGoogle: RadioGroup
    lateinit var browseGoogle: Button
    lateinit var btnNext: Button

    lateinit var usernameGoogle: TextView
    private lateinit var mSharedPref: SharedPreferences

    private var pathG: String? = ""

    val apiInterface = UserApi.create()
    val map: HashMap<String, RequestBody> = HashMap()


    //Image
    private lateinit var imageGoogle: ImageView
    private var multipartImageG: MultipartBody.Part? = null
    private var imageUG: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(/* requestCode = */ requestCode, /* resultCode = */
            resultCode, /* data = */
            data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE) {
            val rl= RealPathUtil()
            val pG: String? = data?.data?.let { this.let { it1 -> rl.getRealPath(it1, it) } }
            if(!pG.isNullOrEmpty()){
                pathG=pG;
            }
            imageGoogle.setImageURI(data?.data)
            imageUG= Uri.parse(data?.dataString!!)
        }


    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMS_REQUEST_CODE ->if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {mSharedPref.edit().putBoolean(IS_GRANTED_READ_IMAGES, true).apply()}
            else { super.onRequestPermissionsResult(requestCode, permissions, grantResults)}
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_google)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);



        imageGoogle = findViewById(R.id.imageViewTGoogle)
        roleGoogle = findViewById(R.id.checkBoxDoctorGoogle)
        certificatGoogle = findViewById(R.id.certificatGooglecertificatGoogle)
        browseGoogle = findViewById(R.id.buttonBrowseGoogle)
        usernameGoogle = findViewById(R.id.textViewUserGoogle)
        btnNext = findViewById(R.id.buttonNext)

        roleGoogle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                certificatGoogle.visibility = View.VISIBLE
                imageGoogle.visibility = View.VISIBLE
            } else {
                certificatGoogle.visibility = View.INVISIBLE
                imageGoogle.visibility = View.INVISIBLE

            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),PERMS_REQUEST_CODE)
        }
        browseGoogle.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE_CODE)
        }

        //Google Sign In

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val gsc = GoogleSignIn.getClient(this, gso);


        val account = GoogleSignIn.getLastSignedInAccount(this)

        if (account != null) {
            usernameGoogle.text = account!!.displayName.toString()

            var email = mSharedPref.getString(EMAIL, "").toString()

            var role = mSharedPref.getString(ROLE, "").toString()

            var certificate = mSharedPref.getString(CERTIFICATE, "").toString()

            Log.e("ROLE", role.toString())

            Log.e("certificate", certificate.toString())

            var fullName = mSharedPref.getString(FULLNAME, "").toString()

            map["email"] = account!!.email.toString().trim().toRequestBody("multipart/form-data".toMediaTypeOrNull())

            map["fullName"] = account!!.displayName.toString().trim().toRequestBody("multipart/form-data".toMediaTypeOrNull())








            btnNext.setOnClickListener {







                //image
                val fG= File(pathG)
                val reqFileG: RequestBody = fG.asRequestBody("image/jpeg".toMediaTypeOrNull())
                Log.e("file",reqFileG.toString())
                val body: MultipartBody.Part=MultipartBody.Part.createFormData("certificate",fG.name,reqFileG)


                var rolee = "patient"

                if (roleGoogle.isChecked) {
                    role = roleGoogle.text.toString()
                    map["role"] = roleGoogle.text.toString().trim().toRequestBody("multipart/form-data".toMediaTypeOrNull())

                }else{
                    role = rolee
                    map["role"] = rolee.trim().toRequestBody("multipart/form-data".toMediaTypeOrNull())



                }


                CoroutineScope(Dispatchers.IO).launch {
                    apiInterface.editedLoginGoogle(
                        mSharedPref.getString(ID, "").toString(),
                        map,body
                    ).enqueue(object : Callback<LoginGoogleResponse> {
                        override fun onResponse(
                            call: Call<LoginGoogleResponse>, response:
                            Response<LoginGoogleResponse>
                        ) {
                            val userGoogle = response.body()
                            Log.e("user", userGoogle.toString())
                            if (userGoogle != null) {
                                mSharedPref.edit().apply {
                                    putString(EMAIL, userGoogle.userGoogle.email)
                                    putString(FULLNAME, userGoogle.userGoogle.fullName)
                                    putString(ROLE, userGoogle.userGoogle.role)
                                    putString(CERTIFICATE, userGoogle.userGoogle.certificate)

                                }.apply()

                                if (userGoogle.userGoogle.role
                                        .equals("doctor")
                                ) {
                                    finish()
                                    val intent =
                                        Intent(this@MainActivityGoogle, MainDoctor::class.java)
                                    startActivity(intent)


                                } else {
                                    finish()
                                    val intent =
                                        Intent(
                                            this@MainActivityGoogle,
                                            MainActivityPatient::class.java
                                        )
                                    startActivity(intent)


                                }


                                var role = mSharedPref.getString(ROLE, "").toString()
                                Log.e("ROLE AFTER", role.toString())

                            }
                        }



                        override fun onFailure(
                            call: Call<LoginGoogleResponse>,
                            t: Throwable
                        ) {
                            Log.e("password: ", t.message.toString())
                            Log.e("fail", ":(")
                        }


                    })
                }

            }


        }


    }
}


























