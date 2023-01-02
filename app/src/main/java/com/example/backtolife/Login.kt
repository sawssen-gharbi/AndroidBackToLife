package com.example.backtolife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.backtolife.API.UserApi
import com.example.backtolife.models.LoginResponse
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


@Suppress("DEPRECATION")
class Login: AppCompatActivity() {



    val apiInterface = UserApi.create()
    val map: HashMap<String, String> = HashMap()


    lateinit var imageFacebook : ImageView
    lateinit var imageGoogle : ImageView
    lateinit var email : TextInputLayout
    lateinit var password : TextInputLayout
    private lateinit var mSharedPref: SharedPreferences
    lateinit var callbackManager : CallbackManager
    lateinit var accessToken : AccessToken
    private var fpassword: TextView?=null






    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        email = findViewById(R.id.textInputLayoutEmail)
        password = findViewById(R.id.textInputLayoutPassword)
        imageGoogle = findViewById(R.id.imageViewGoogle)
        imageFacebook =findViewById(R.id.imageViewFacebook)
        fpassword=findViewById<TextView>(R.id.textViewForgetPassword)




        val button = findViewById<Button>(R.id.buttonLogin)
        val but = findViewById<Button>(R.id.textButtonLogin)

        button.setOnClickListener {
            doLogin()
        }

        but.setOnClickListener {
            val intent = Intent(this@Login, SignUp::class.java)
            startActivity(intent)
        }

        fpassword!!.setOnClickListener{
            val mainIntent = Intent(this, forget_password ::class.java)
            startActivity(mainIntent)
            fpassword!!.movementMethod = LinkMovementMethod.getInstance();
        }


        //Google Sign In

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val gsc = GoogleSignIn.getClient(this, gso);



        fun signIn() {
            val signInIntent: Intent = gsc.signInIntent
            startForResult.launch(signInIntent)
        }

        imageGoogle.setOnClickListener{
            signIn()

        }

        //Facebook sign in

        callbackManager = CallbackManager.Factory.create();








        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    accessToken = result.accessToken

                    if (!accessToken.isExpired && accessToken != null) {
                        val request = GraphRequest.newMeRequest(
                            accessToken,
                            object : GraphRequest.GraphJSONObjectCallback{
                                override fun onCompleted(
                                    obj: JSONObject?,
                                    response: GraphResponse?
                                ) {

                                    var fullnameFb : String = obj!!.getString("name")
                                    var emailFb : String = obj!!.getString("email")
                                    map["email"] = emailFb
                                    map["fullName"] = fullnameFb
                                    map["role"] = String()
                                    map["certificate"] = String()
                                    CoroutineScope(Dispatchers.IO).launch {
                                        apiInterface.loginFacebook(map)
                                            .enqueue(object : Callback<LoginFacebookResponse> {
                                                override fun onResponse(
                                                    call: Call<LoginFacebookResponse>, response:
                                                    Response<LoginFacebookResponse>
                                                ) {
                                                    val userFacebook = response.body()
                                                    Log.e("user there", userFacebook.toString())
                                                    if (userFacebook != null) {
                                                        mSharedPref.edit().apply {
                                                            putString(
                                                                ID,
                                                                userFacebook.userFacebook._id
                                                            )
                                                            putString(
                                                                EMAIL,
                                                                userFacebook.userFacebook.email
                                                            )
                                                            putString(
                                                                FULLNAME,
                                                                userFacebook.userFacebook.fullName
                                                            )
                                                            putString(
                                                                ROLE,
                                                                userFacebook.userFacebook.role
                                                            )
                                                            putString(
                                                                CERTIFICATE,
                                                                userFacebook.userFacebook.certificate
                                                            )

                                                        }.apply()

                                                        if (userFacebook.userFacebook.role.equals("")) {
                                                            finish()
                                                            val intent = Intent(
                                                                this@Login,
                                                                MainActivityFacebook::class.java
                                                            )
                                                            startActivity(intent)

                                                        } else {
                                                            if (userFacebook.userFacebook.role.equals(
                                                                    "patient"
                                                                )
                                                            ) {
                                                                finish()
                                                                val intent = Intent(
                                                                    this@Login,
                                                                    MainActivityPatient::class.java
                                                                )
                                                                startActivity(intent)
                                                            } else {
                                                                finish()
                                                                val intent =
                                                                    Intent(
                                                                        this@Login,
                                                                        MainDoctor::class.java
                                                                    )
                                                                startActivity(intent)

                                                            }
                                                        }


                                                    }
                                                }

                                                override fun onFailure(
                                                    call: Call<LoginFacebookResponse>,
                                                    t: Throwable
                                                ) {
                                                    Log.e("fail facebook", t.message.toString())
                                                }
                                            })
                                    }



                                }
                            })
                        val parameters = Bundle()
                        parameters.putString("fields", "id,name,link,email")
                        request.parameters = parameters
                        request.executeAsync()

                    }

                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    // App code
                }
            })



        imageFacebook.setOnClickListener{


            LoginManager.getInstance().logInWithReadPermissions(this@Login, Arrays.asList("public_profile","email"));

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
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

            Log.e("account",account.toString())
            map["email"] = account!!.email.toString()
            map["fullName"] = account!!.displayName.toString()
            map["role"] = String()
            map["certificate"] = String()
            CoroutineScope(Dispatchers.IO).launch {
                apiInterface.loginGoogle(map).enqueue(object : Callback<LoginGoogleResponse> {
                    override fun onResponse(
                        call: Call<LoginGoogleResponse>, response:
                        Response<LoginGoogleResponse>
                    ) {
                        val userGoogle = response.body()
                        Log.e("user there", userGoogle.toString())
                        if (userGoogle != null) {
                            mSharedPref.edit().apply {
                                putString(ID, userGoogle.userGoogle._id)
                                putString(EMAIL, userGoogle.userGoogle.email)
                                putString(FULLNAME, userGoogle.userGoogle.fullName)
                                putString(ROLE, userGoogle.userGoogle.role)
                                putString(CERTIFICATE, userGoogle.userGoogle.certificate)

                            }.apply()

                            if (userGoogle.userGoogle.role.equals("")) {
                                finish()
                                val intent = Intent(this@Login, MainActivityGoogle::class.java)
                                startActivity(intent)

                            }else {
                                if (userGoogle.userGoogle.role.equals("patient")) {
                                    finish()
                                    val intent = Intent(this@Login, MainActivityPatient::class.java)
                                    startActivity(intent)
                                } else {
                                    finish()
                                    val intent = Intent(this@Login, MainDoctor::class.java)
                                    startActivity(intent)

                                }
                            }



                        }
                    }



                    override fun onFailure(call: Call<LoginGoogleResponse>, t: Throwable) {
                        Log.e("fail", ":(")
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
                                "Please Check The Email Or The Password",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.e("Login: ", t.message.toString())
                        Toast.makeText(this@Login,"Connexion error!", Toast.LENGTH_SHORT).show()
                    }



                })
            }
        }
    }

    private fun isValide(): Boolean {
        if ((email.editText?.text.toString()).isEmpty()) {
            email.error = "Email Can't Be Empty"
            return false
        }else {
            email.error = null
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.editText?.text.toString()).matches()) {
            email.error = "Invalid Email Address"
            return false
        }else {
            email.error = null
        }

        if ((password.editText?.text.toString()).isEmpty()){
            password.error = "Password Cannot Be Empty"
            return false
        }else {
            password.error = null
        }


        return true
    }
}