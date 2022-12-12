package com.example.backtolife

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import org.json.JSONObject


class MainActivityFacebook: AppCompatActivity() {
    lateinit var usernameFacebook: TextView
     lateinit var accessToken : AccessToken
    lateinit var  fullName : String
    lateinit var  email : String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_facebook)

        usernameFacebook = findViewById(R.id.textViewUserFacebook)

        accessToken = AccessToken.getCurrentAccessToken()!!

        val request = GraphRequest.newMeRequest(
            accessToken,
            object : GraphRequest.GraphJSONObjectCallback {
                override fun onCompleted(
                    obj: JSONObject?,
                    response: GraphResponse?
                ) {
                   fullName = obj?.getString("name").toString()

                    if (obj != null) {
                        if (obj.has("email")) {
                            email = obj.getString("email")
                        }
                    }
                    usernameFacebook.text = fullName
                    Log.e("email",email.toString())
                }
            })
        val parameters = Bundle()
        parameters.putString("fields", "id,name,link,email")
        request.parameters = parameters
        request.executeAsync()
    }
}