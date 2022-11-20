package com.example.backtolife

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.backtolife.API.UserApi
import com.example.backtolife.models.LoginResponse
import com.example.backtolife.models.SignupResponse
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val PREF_NAME = "DATA_PREF"
const val ID = "ID"
const val ADDRESS = "ADDRESS"
const val EMAIL = "EMAIL"
const val FULLNAME = "FULLNAME"
const val PASSWORD = "PASSWORD"
const val PHONE = "PHONE"
const val SPECIALITY = "SPECIALITY"
const val CERTIFICAT = "CERTIFICAT"
class SignUp : AppCompatActivity() {

    lateinit var email: TextInputLayout
    lateinit var password: TextInputLayout
    lateinit var fullname: TextInputLayout
    lateinit var role: CheckBox
    lateinit var certificat : RadioGroup
    lateinit var browse : Button
    lateinit var btn: Button
    lateinit var bttn: Button
    private lateinit var mSharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
            val log=findViewById<Button>(R.id.textButtonLogin)

            email = findViewById(R.id.textInputLayoutEmail)
            fullname = findViewById(R.id.textInputLayoutFullName)
            role = findViewById(R.id.checkBoxDoctor)
            password = findViewById(R.id.textInputLayoutPassword)
            mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            btn = findViewById<Button>(R.id.buttonSignUp)
            bttn = findViewById<Button>(R.id.textButtonLogin)
            certificat = findViewById(R.id.radioGroupCertificat)
             browse = findViewById(R.id.buttonBrowse)

        role.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                certificat.visibility = View.VISIBLE

            }
            else{
                certificat.visibility = View.INVISIBLE

            }
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
            val map: HashMap<String, String> = HashMap()

            map["fullName"] = fullname.editText?.text.toString()
            map["email"] = email.editText?.text.toString()
            map["password"] = password.editText?.text.toString()
            if(role.isChecked){
                map["role"] = role.text.toString()
            }else{
                map["role"] = rolee.toString()
            }

            map["certificat"] = browse.text.toString()


            CoroutineScope(Dispatchers.IO).launch {
                apiInterface.signup(map).enqueue(object : Callback<SignupResponse> {
                    override fun onResponse(
                        call: Call<SignupResponse>, response:
                        Response<SignupResponse>
                    ) {

                        val user = response.body()
                        Log.e("success: ", user.toString())
                        if (user != null  ) {
                            mSharedPref.edit().apply {
                                putString(ID, user.user._id)
                                putString(PASSWORD, user.user.password)
                                putString(EMAIL, user.user.email)
                                putString(FULLNAME, user.user.fullName)
                                putString(CERTIFICAT,user.user.certificat)
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
                        Log.e("password: ", t.message.toString())
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

