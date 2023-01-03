package com.example.backtolife

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.backtolife.API.UserApi
import com.example.backtolife.models.UserReset
import com.example.backtolife.models.UserResetResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class verificationsingup : AppCompatActivity() {
    lateinit var btncontinue: Button
    lateinit var code1:EditText
    lateinit var code2:EditText
    lateinit var code3:EditText
    lateinit var code4:EditText
    lateinit var resend:TextView
    private lateinit var mSharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verificationsingup)







                title="Verification"
                btncontinue=findViewById(R.id.bt_Continue)
                code1=findViewById(R.id.code1)
                code2=findViewById(R.id.code2)
                code3=findViewById(R.id.code3)
                code4=findViewById(R.id.code4)
                resend=findViewById(R.id.bt_btnresend)
                val email = intent.getStringExtra(EMAIL).toString()



//        viewInitializations()
                btncontinue.setOnClickListener {

                    if(validate()){
                        val mainIntent = Intent(this, resetpassword::class.java).apply {
                            putExtra(EMAIL,email)

                        }
                        startActivity(mainIntent)
                    }
                    else
                        Toast.makeText(applicationContext, "incorrect code !!", Toast.LENGTH_LONG).show()


                }
                resend.setOnClickListener{
                    var userReset = UserReset()
                    userReset.email = intent.getStringExtra(EMAIL).toString()
                    val reset1=(0..9).random()
                    val reset2=(0..9).random()
                    val reset3=(0..9).random()
                    val reset4=(0..9).random()
                    mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                    val id: String? =mSharedPref.getString(ID, "")


                    userReset.resetCode= reset1.toString()+reset2.toString()+reset3.toString()+reset4.toString()
                    val apiuser = UserApi.create()
                    apiuser.sendResetCode(userReset).enqueue(object :
                        Callback<UserResetResponse> {
                        override fun onResponse(call: Call<UserResetResponse>, response:
                        Response<UserResetResponse>
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


                                val intent = Intent(this@verificationsingup,verificationsingup::class.java)
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



private fun validate(): Boolean {
                if(code1.text.toString().equals(intent.getStringExtra(RESET1).toString())
                    &&code2.text.toString().equals(intent.getStringExtra(RESET2).toString())
                    &&code3.text.toString().equals(intent.getStringExtra(RESET3).toString())
                    &&code4.text.toString().equals(intent.getStringExtra(RESET4).toString())) {
                    return true
                } else
                    return false


            }
        }


/* fun viewInitializations() {
     etEmail = findViewById(R.id.et_email)
     // To show back button in actionbar
     supportActionBar?.setDisplayHomeAsUpEnabled(true)
 }
 fun validateInput(): Boolean {
     if (etEmail.text.toString().equals("")) {
         etEmail.setError("Please Enter your verification code")
         return false
     }
     // checking the proper email format
     return true
 }
 fun performForgetPassword (view: View) {
     if (validateInput()) {
         // Input is valid, here send data to your server
         val intent = Intent(this, MainActivity::class.java)
         startActivity(intent)
         Toast.makeText(this,"verified", Toast.LENGTH_SHORT).show()
         // Here you can call you API
     }
 }*/
