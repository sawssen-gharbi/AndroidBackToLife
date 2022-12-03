package com.example.backtolife

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.backtolife.API.UserApi
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityGoogle : AppCompatActivity() {

    lateinit var roleGoogle: CheckBox
    lateinit var certificatGoogle: RadioGroup
    lateinit var browseGoogle: Button
    lateinit var btnNext: Button

    lateinit var usernameGoogle: TextView
    private lateinit var mSharedPrefG: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_google)
        mSharedPrefG = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        val apiInterface = UserApi.create()
        val map: HashMap<String, String> = HashMap()


        roleGoogle = findViewById(R.id.checkBoxDoctorGoogle)
        certificatGoogle = findViewById(R.id.certificatGooglecertificatGoogle)
        browseGoogle = findViewById(R.id.buttonBrowseGoogle)
        usernameGoogle = findViewById(R.id.textViewUserGoogle)
        btnNext = findViewById(R.id.buttonNext)

        roleGoogle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                certificatGoogle.visibility = View.VISIBLE

            } else {
                certificatGoogle.visibility = View.INVISIBLE

            }
        }

        //Google Sign In

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val gsc = GoogleSignIn.getClient(this, gso);

        val account = GoogleSignIn.getLastSignedInAccount(this)

        usernameGoogle.text = account!!.displayName.toString()




        map["fullName"] = account.displayName.toString()
        map["email"] = account.email.toString()


        btnNext.setOnClickListener {

            var rolee = "patient"

            if (roleGoogle.isChecked) {
                map["role"] = roleGoogle.text.toString()
            } else {
                map["role"] = rolee.toString()
            }

            map["certificat"] = browseGoogle.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                apiInterface.loginGoogle(map).enqueue(object : Callback<LoginGoogleResponse> {
                    override fun onResponse(
                        call: Call<LoginGoogleResponse>, response:
                        Response<LoginGoogleResponse>
                    ) {
                        val userGoogle = response.body()
                        if (userGoogle != null) {
                            Log.e("success: ", userGoogle.toString())
                            mSharedPrefG.edit().apply {
                                putString(EMAIL, userGoogle.userGoogle.email)
                                putString(FULLNAME, userGoogle.userGoogle.fullName)
                                putString(ROLE, userGoogle.userGoogle.role)

                            }.apply()

                                if (userGoogle.userGoogle.role.equals("doctor")) {
                                    val intent =
                                        Intent(this@MainActivityGoogle, MainDoctor::class.java)
                                    startActivity(intent)
                                    finish()


                                } else {

                                    val intent =
                                        Intent(
                                            this@MainActivityGoogle,
                                            MainActivityPatient::class.java
                                        )
                                    startActivity(intent)
                                    finish()

                                }
                            }
                        }



                    override fun onFailure(call: Call<LoginGoogleResponse>, t: Throwable) {
                        Log.e("fail", ":(")
                    }


                })
            }

        }
    }
}














