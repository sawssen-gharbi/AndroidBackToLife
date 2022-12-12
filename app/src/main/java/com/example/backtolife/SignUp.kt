package com.example.backtolife

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.backtolife.API.UserApi
import com.example.backtolife.models.SignupResponse
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.Optional.empty


const val PREF_NAME = "DATA_PREF"
const val ID = "ID"
const val ROLE = "ROLE"
const val ADDRESS = "ADDRESS"
const val EMAIL = "EMAIL"
const val FULLNAME = "FULLNAME"
const val PASSWORD = "PASSWORD"
const val PHONE = "PHONE"
const val SPECIALITY = "SPECIALITY"
const val CERTIFICATE = "CERTIFICATE"

//Image
const val PICK_IMAGE_CODE = 100
const val PERMS_REQUEST_CODE = 101
const val IS_GRANTED_READ_IMAGES = "IS_GRANTED_READ_IMAGES"
const val PREFS_NAME = "APP_PREFS"
const val REQUEST_GALLERY = 9544
class SignUp : AppCompatActivity() {


    lateinit var email: TextInputLayout
    lateinit var password: TextInputLayout
    lateinit var fullname: TextInputLayout
    lateinit var role: CheckBox
    lateinit var certificat: RadioGroup
    lateinit var browse: Button
    lateinit var btn: Button
    lateinit var bttn: Button


    private lateinit var mSharedPref: SharedPreferences


    //Image
    private  var path: String? =""
    private lateinit var image: ImageView
    private var multipartImage: MultipartBody.Part? = null
    private var imageU: Uri? = null
    private  var body: MultipartBody.Part?= null
    private var reqFile: RequestBody? = null


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(/* requestCode = */ requestCode, /* resultCode = */
            resultCode, /* data = */
            data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE) {
            val rl=RealPathUtil()
            val p: String? = data?.data?.let { this.let { it1 -> rl.getRealPath(it1, it) } }
            if(!p.isNullOrEmpty()){
                path=p;
            }
            image.setImageURI(data?.data)
            imageU= Uri.parse(data?.dataString!!)
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

        setContentView(R.layout.activity_signup)
        val log = findViewById<Button>(R.id.textButtonLogin)


        email = findViewById(R.id.textInputLayoutEmail)
        fullname = findViewById(R.id.textInputLayoutFullName)
        role = findViewById(R.id.checkBoxDoctor)
        password = findViewById(R.id.textInputLayoutPassword)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        btn = findViewById<Button>(R.id.buttonSignUp)
        bttn = findViewById<Button>(R.id.textButtonLogin)
        certificat = findViewById(R.id.radioGroupCertificat)
        browse = findViewById(R.id.buttonBrowse)
        image = findViewById(R.id.imageViewT)

        role.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                certificat.visibility = View.VISIBLE
                image.visibility = View.VISIBLE

            } else {
                certificat.visibility = View.INVISIBLE
                image.visibility = View.INVISIBLE
            }
        }


        browse.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE_CODE)
        }

        btn.setOnClickListener {
            Register()
        }

        log.setOnClickListener{
            val intent = Intent(this@SignUp, Login::class.java)
            startActivity(intent)
        }
    }



    private fun Register() {
        var rolee = "patient"
        if (isValide()) {


            val apiInterface = UserApi.create()
            val map: HashMap<String, RequestBody> = HashMap()

            //image
            val f=File(path)


            map["fullName"] = fullname.editText?.text.toString().trim().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            map["email"] = email.editText?.text.toString().trim().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            map["password"] = password.editText?.text.toString().trim().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            if(role.isChecked) {
                if (f != null) {
                    Log.e("file", reqFile.toString())

                    reqFile = f.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    body = MultipartBody.Part.createFormData("certificate", f.name, reqFile!!)
                    map["role"] = role.text.toString().trim()
                        .toRequestBody("multipart/form-data".toMediaTypeOrNull())
                } else {
                    map["role"] =
                        rolee.trim().toRequestBody("multipart/form-data".toMediaTypeOrNull())

                    //reqFile = f.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    //body = MultipartBody.Part.createFormData("certificate", "", reqFile!!)

                }
            }



            CoroutineScope(Dispatchers.IO).launch {
                apiInterface.signup(map, body!!).enqueue(object : Callback<SignupResponse> {
                    override fun onResponse(
                        call: Call<SignupResponse>, response:
                        Response<SignupResponse>
                    ) {

                        val user = response.body()
                        Log.e("success: ", user.toString())
                        if (user != null  ) {
                            mSharedPref.edit().apply {
                                putString(ID, user.user._id)
                                putString(ROLE, user.user.role)
                                putString(PASSWORD, user.user.password)
                                putString(EMAIL, user.user.email)
                                putString(FULLNAME, user.user.fullName)
                                putString(CERTIFICATE,user.user.certificate)
                            }.apply()

                            intent = Intent(this@SignUp, Login::class.java)
                            startActivity(intent)
                        } else {

                            val intent = Intent(this@SignUp, SplashScreen::class.java)
                            startActivity(intent)
                            finish()

                        }
                    }


                    override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                        Log.e("certificate ", t.message.toString())
                        Toast.makeText(this@SignUp, "Connexion error!", Toast.LENGTH_SHORT)
                            .show()

                    }
                })
            }
        }


    }
}


fun isValide(): Boolean {
    return true
}

