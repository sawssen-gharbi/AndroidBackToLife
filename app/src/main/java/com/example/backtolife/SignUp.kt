package com.example.backtolife

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.backtolife.API.UserApi
import com.example.backtolife.models.SignupResponse
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class SignUp : AppCompatActivity() {
     val PREF_NAME = "DATA_PREF"
     val ID = "ID"
    lateinit var email: TextInputLayout
    lateinit var password: TextInputLayout
    lateinit var fullname: TextInputLayout
    lateinit var role: CheckBox
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
        btn.setOnClickListener {
            Register()

        }
        log.setOnClickListener{
            val intent = Intent(this@SignUp, Login::class.java)
            startActivity(intent)
        }
    }

    private fun Register() {
        if (isValide()) {
            val apiInterface = UserApi.create()
            val map: HashMap<String, String> = HashMap()

            map["fullName"] = fullname.editText?.text.toString()
            map["email"] = email.editText?.text.toString()
            map["password"] = password.editText?.text.toString()
            map["role"] = role.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                apiInterface.signup(map).enqueue(object : Callback<SignupResponse> {
                    @SuppressLint("SuspiciousIndentation")
                    override fun onResponse(
                        call: Call<SignupResponse>, response:
                        Response<SignupResponse>
                    ) {

                        val user = response.body()
                        if(role.isChecked) {
                            role.text ="doctor"
                        }else{
                            role.text="patient"
                        }


                        if (user != null  ) {
                            mSharedPref.edit().apply {
                                putString(ID, user.user._id)
                                putString(FULLNAME,user.user.fullName)

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


