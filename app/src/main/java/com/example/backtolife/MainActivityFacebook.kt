package com.example.backtolife

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import org.json.JSONObject


class MainActivityFacebook : AppCompatActivity() {
    lateinit var roleFacebook: CheckBox
    lateinit var certificatFacebook:  RadioGroup
    lateinit var browseFacebook: Button
    lateinit var buttonNextFacebook: Button

    lateinit var usernameFacebook: TextView
    private lateinit var mSharedPref: SharedPreferences

    private lateinit var accessToken : AccessToken

    private lateinit var  fullname : String

    private lateinit var  email : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_facebook)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        usernameFacebook = findViewById(R.id.textViewUserFacebook)

        accessToken = AccessToken.getCurrentAccessToken()!!

        val request = GraphRequest.newMeRequest(
            accessToken,
            object : GraphRequest.GraphJSONObjectCallback{
                override fun onCompleted(
                    obj: JSONObject?,
                    response: GraphResponse?
                ) {

                   fullname = obj!!.getString("name")
                    usernameFacebook.text = fullname.trim()

                }
            })
        val parameters = Bundle()
        parameters.putString("fields", "id,name,link,email")
        request.parameters = parameters
        request.executeAsync()

    }
}