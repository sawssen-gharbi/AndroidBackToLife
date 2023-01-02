package com.example.backtolife

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.backtolife.API.UserApi
import com.example.backtolife.models.User
import com.example.backtolife.models.UserResetPassword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class resetpassword : AppCompatActivity() {
    lateinit var btn:Button
    lateinit var ps1:EditText
    lateinit var ps2:EditText
    lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resetpassword)
        mSharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        btn=findViewById(R.id.Confirm)
        ps1=findViewById(R.id.txtpswd)
        ps2=findViewById(R.id.txtpswd2)
        println("yesmina"+intent.getStringExtra(EMAIL).toString())


        btn.setOnClickListener{
            var passRes = UserResetPassword()
            if(ps1?.text.toString() == ps2?.text.toString()){
                passRes.email = intent.getStringExtra(EMAIL).toString()
                passRes.password = ps1?.text.toString()
                val apiuser =
                    UserApi.create().changePasswordReset(passRes)
                apiuser.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            Toast.makeText(applicationContext, "good", Toast.LENGTH_LONG).show()
                            Log.i("messageeee:",passRes.email.toString())
                            //loadingDialog.dismissDialog()
                            val mainIntent= Intent(this@resetpassword,Login::class.java)
                            startActivity(mainIntent)
                        } else {
                            Toast.makeText(applicationContext, "uncorrect email", Toast.LENGTH_LONG).show()
                            Log.i("RETROFIT_API_RESPONSE", response.toString())
                            Log.i("login response", response.body().toString())
                        }

                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(applicationContext, "erreur server", Toast.LENGTH_LONG).show()
                        println("aaaaaa")                    }
                })
            }else {
                println("el passwords moch kifkif")
            }

        }

    }

}