package com.example.backtolife

import android.R.attr.data
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.backtolife.API.UserApi
import com.example.backtolife.models.LoginResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login: AppCompatActivity() {



    val apiInterface = UserApi.create()
    val map: HashMap<String, String> = HashMap()



    lateinit var imageGoogle : ImageView
    lateinit var email : TextInputLayout
    lateinit var password : TextInputLayout
    private lateinit var mSharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        email = findViewById(R.id.textInputLayoutEmail)
        password = findViewById(R.id.textInputLayoutPassword)
        imageGoogle = findViewById(R.id.imageViewGoogle)




        val button = findViewById<Button>(R.id.buttonLogin)
        val but = findViewById<Button>(R.id.textButtonLogin)

        button.setOnClickListener {
            doLogin()
        }

        but.setOnClickListener {
            val intent = Intent(this@Login, SignUp::class.java)
            startActivity(intent)
        }


        //Google Sign In

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val gsc = GoogleSignIn.getClient(this, gso);
        val account = GoogleSignIn.getLastSignedInAccount(this)

         fun signIn() {
            val signInIntent: Intent = gsc.signInIntent
            startForResult.launch(signInIntent)
        }

        imageGoogle.setOnClickListener{
                signIn()

        }

    }




    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)
            handleSignInResult(task)

        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
             val account = completedTask.getResult(ApiException::class.java)
            val acc = account.email
            map["email"] = acc.toString()
            map["fullName"] = account.displayName.toString()

            CoroutineScope(Dispatchers.IO).launch {
                apiInterface.loginGoogle(map).enqueue(object : Callback<LoginGoogleResponse> {
                    override fun onResponse(
                        call: Call<LoginGoogleResponse>, response:
                        Response<LoginGoogleResponse>
                    ) {
                        val userGoogle = response.body()
                        // Log.e("success: ", userInfo.toString())
                        if (userGoogle != null) {
                            mSharedPref.edit().apply {
                                putString(EMAIL, account.email)
                                putString(FULLNAME, account.displayName)
                            }.apply()

                                val intent = Intent(this@Login, MainActivityPatient::class.java)
                                startActivity(intent)
                                finish()



                        } else {

                            Toast.makeText(
                                this@Login,
                                "Mot de passe incorrect !!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }

                    override fun onFailure(call: Call<LoginGoogleResponse>, t: Throwable) {
                        Log.e("ggg","gggg")
                    }

                })



            }

        } catch (e: ApiException) {
            Log.d("Message",e.toString())
        }
    }




    private fun doLogin() {
        if (isValide()){


            map["email"] = email.editText?.text.toString()
            map["password"] = password.editText?.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                apiInterface.login(map).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response:
                    Response<LoginResponse>
                    ) {
                        val userInfo = response.body()
                       // Log.e("success: ", userInfo.toString())
                        if (userInfo != null) {
                            mSharedPref.edit().apply {
                                putString(ID, userInfo.userInfo._id)
                                putString(PASSWORD, userInfo.userInfo.password)
                                putString(EMAIL, userInfo.userInfo.email)
                                putString(FULLNAME, userInfo.userInfo.fullName)
                                putString(PASSWORD,userInfo.userInfo.password)
                                putString(PHONE,userInfo.userInfo.phone)
                                putString(SPECIALITY,userInfo.userInfo.speciality)
                                putString(CERTIFICAT,userInfo.userInfo.certificat)
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
        if ((email.editText?.text.toString()).isEmpty()){
            email.error = "xx"
            return false
        }

        if ((password.editText?.text.toString()).isEmpty()){
            password.error = "xx"
            return false
        }

        return true
    }
}