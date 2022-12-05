package com.example.backtolife

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.backtolife.API.UserApi
import com.example.backtolife.models.LoginResponse
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
class Login: AppCompatActivity() {
    private var fpassword:TextView?=null

    lateinit var email : TextInputLayout
    lateinit var password : TextInputLayout
    private lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email = findViewById(R.id.textInputLayoutEmail)
        password = findViewById(R.id.textInputLayoutPassword)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
       val button = findViewById<Button>(R.id.buttonLogin)
        val but=findViewById<Button>(R.id.textButtonLogin)
        fpassword=findViewById<TextView>(R.id.textViewForgetPassword)
        button.setOnClickListener{
            doLogin()


        }
        but.setOnClickListener{
            val intent = Intent(this@Login, SignUp::class.java)
            startActivity(intent)
        }
        fpassword!!.setOnClickListener{
            val mainIntent = Intent(this, forget_password ::class.java)
            startActivity(mainIntent)
            fpassword!!.movementMethod = LinkMovementMethod.getInstance();
        }





    }

    private fun doLogin() {
        if (isValide()){
            val apiInterface = UserApi.create()
            val map: HashMap<String, String> = HashMap()

            map["email"] = email.editText?.text.toString()
            map["password"] = password.editText?.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                apiInterface.login(map).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response:
                    Response<LoginResponse>
                    ) {
                        val userInfo = response.body()
                        //Log.e("success: ", userInfo.toString())
                        if (userInfo != null) {
                            mSharedPref.edit().apply {
                                putString(ID, userInfo.userInfo._id)
                                putString(PASSWORD, userInfo.userInfo.password)
                                putString(EMAIL, userInfo.userInfo.email)

                                Log.e("you are innnnnnnnnnn: ", userInfo.userInfo.email)

                                //putStringSet(FOLLOWERSARRAY,user.followers)

                            }.apply()
                            if (userInfo.userInfo.role.equals("doctor")) {
                                val intent = Intent(this@Login, MainDoctor::class.java)
                                startActivity(intent)
                                finish()


                            } else {

                                val intent = Intent(this@Login, MainActivityPatient::class.java)
                                startActivity(intent)
                                finish()

                            }
                        }
                            else {

                                Toast.makeText(
                                    this@Login,
                                    "Mot de passe incorrect !!",
                                    Toast.LENGTH_SHORT
                                ).show()
                        }
                    }
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.e("password: ", t.message.toString())
                        Toast.makeText(this@Login,"Connexion error!", Toast.LENGTH_SHORT).show()
                    }



                })
            }
        }
    }

    private fun isValide(): Boolean {
        return true

    }

}