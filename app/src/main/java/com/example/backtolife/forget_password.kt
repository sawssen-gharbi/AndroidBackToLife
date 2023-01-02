package com.example.backtolife

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.backtolife.API.UserApi
import com.example.backtolife.models.UserReset
import com.example.backtolife.models.UserResetResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val RESET1="RESET1"
const val RESET2="RESET2"
const val RESET3="RESET3"
const val RESET4="RESET4"
private lateinit var mSharedPref: SharedPreferences


class forget_password : AppCompatActivity() {
    lateinit var etEmail: EditText
    lateinit var btnlogin:Button
    lateinit var ctn : Button
    val apiuser = UserApi.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        title="Resset Password"
        btnlogin = findViewById(R.id.bt_login)
        ctn = findViewById(R.id.bt_forget)


        viewInitializations()

        btnlogin.setOnClickListener {
            val mainIntent = Intent(this, Login::class.java)
            startActivity(mainIntent)
            finish()
        }
            ctn.setOnClickListener{
                performForgetPassword(it)
            }

        }


    fun viewInitializations() {

        etEmail = findViewById(R.id.et_email)


        // To show back button in actionbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }


    fun validateInput(): Boolean {

        if (etEmail.text.toString().equals("")) {
            etEmail.setError("Please Enter Email")
            return false
        }
        // checking the proper email format
        if (!isEmailValid(etEmail.text.toString())) {
            etEmail.setError("Please Enter Valid Email")
            return false
        }
        return true
    }

    fun isEmailValid(email: String): Boolean {
        return EMAIL_ADDRESS.matcher(email).matches()
    }

    fun performForgetPassword (view: View) {
        if (validateInput()) {



            val email = etEmail.text.toString()




            var userReset = UserReset()
            userReset.email = email
            val reset1=(0..9).random()
            val reset2=(0..9).random()
            val reset3=(0..9).random()
            val reset4=(0..9).random()
            mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            val id: String? =mSharedPref.getString(ID, "")


            userReset.resetCode= reset1.toString()+reset2.toString()+reset3.toString()+reset4.toString()

            apiuser.sendResetCode(userReset).enqueue(object :
                Callback<UserResetResponse> {
                override fun onResponse(
                    call: Call<UserResetResponse>,
                    response: Response<UserResetResponse>
                ) {
                    println("++++++++++++++response" + response.body()?.msgg.toString())
                    if (response.isSuccessful) {

                        if (response.body()?.msgg.toString() == "false") {
                            Toast.makeText(
                                applicationContext,
                                "email non valid",
                                Toast.LENGTH_SHORT
                            ).show()


                            if (response.body()?.msgg.toString() == "true") {
                                Toast.makeText(
                                    applicationContext,
                                    "code will be send to your email",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }


                        }


                        val intent = Intent(applicationContext, verificationsingup::class.java).apply {
                            putExtra(RESET1,reset1.toString())
                            putExtra(RESET2,reset2.toString())
                            putExtra(RESET3,reset3.toString())
                            putExtra(RESET4,reset4.toString())
                            putExtra(EMAIL,email)

                        }

                        startActivity(intent)


                    } else {

                        Toast.makeText(applicationContext, "erreur", Toast.LENGTH_LONG)
                            .show()

                    }
                }

                override fun onFailure(call: Call<UserResetResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "erreur server", Toast.LENGTH_LONG)
                        .show()
                }

            })
        }


    }


}