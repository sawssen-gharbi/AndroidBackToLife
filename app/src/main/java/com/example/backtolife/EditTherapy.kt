package com.example.backtolife

import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.backtolife.API.UserApi
import com.example.backtolife.fragments.isValide
import com.example.backtolife.models.Therapy
import com.example.backtolife.models.TherapyResponse
import com.example.backtolife.models.User
import com.example.studentchat.Interface.RealPathUtil
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class EditTherapy : AppCompatActivity() {



    private  lateinit var titre : TextInputLayout
    private lateinit var date : Button
    private  lateinit var str1:String

    private lateinit var butLoc:Button

    private  lateinit var textSelectedDate:TextView
    private  var imageU :Uri?=null
    private lateinit var address:TextInputLayout
    private lateinit var image: ImageView
    private  var multipartImage: MultipartBody.Part? = null
    private  lateinit var path:String



    private lateinit var mSharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_therapy)


      image=findViewById(R.id.lottie1)

        titre= findViewById(R.id.textInputLayoutFullNameProfile)
        // date=view.findViewById(R.id.mtrl_picker_text_input_date)
        date =findViewById<Button>(R.id.textButtonDate)
        address=findViewById(R.id.textInputLayoutUsernameProfile)
        textSelectedDate=findViewById(R.id.textViewSelectedDate)
        mSharedPref = this.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        var address1 = mSharedPref.getString(ADDRESS,"")
        address.editText!!.setText(address1.toString())
        var titre1 = mSharedPref.getString(TITRE,"")
        titre.editText!!.setText(titre1.toString())
        var dateee = mSharedPref.getString(DATE,"")
        textSelectedDate.text=dateee.toString()
        val imagee = intent.getStringExtra(IMAGE).toString()
        if(!imagee.isNullOrEmpty() && imagee != "empty"){
            Glide.with(this)
                .load(imagee)
                .into(image)

            image.visibility= View.VISIBLE
        }

        val calender = Calendar.getInstance()
        fun updateTable(c: Calendar) {
            val mf = SimpleDateFormat("dd-MM-yyyy")
            val sdf = mf.format(c.time)
            textSelectedDate.text = sdf

        }

        val datepicker = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, month)
            calender.set(Calendar.DAY_OF_MONTH, day)

            updateTable(calender)

        }




        date.setOnClickListener {
            DatePickerDialog(this,datepicker,calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH)).show()

        }








        val bot = findViewById<Button>(R.id.button3)
        bot.setOnClickListener {
            doUpdate()


        }
    }
    private fun doUpdate() {
        if (isValide()){
            val apiInterface = UserApi.create()




            // val reqFile = f.asRequestBody("image/jpeg".toMediaTypeOrNull())
            //val body: MultipartBody.Part=MultipartBody.Part.createFormData("image",f.name,reqFile)

            val map: HashMap<String, RequestBody> = HashMap()
            map["date"] =   textSelectedDate.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            map["titre"] = titre.editText?.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            map["address"]=address.editText?.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())

            //map["image"]= body

            CoroutineScope(Dispatchers.IO).launch {
                apiInterface.updatePostSansImage(map).enqueue(object : Callback<TherapyResponse> {
                    // ija discord hhh
                    override fun onResponse(call: Call<TherapyResponse>, response:
                    Response<TherapyResponse>
                    ) {
                        val therapy = response.body()
                        Log.e("success: ", therapy.toString())
                        if (therapy != null) {
                            mSharedPref.edit().apply {
                                putString(IDTHERAPY, therapy.therapy._id)
                                putString(TITRE, therapy.therapy.titre)
                                putString(DATE, therapy.therapy.date)
                                putString(IMAGE,therapy.therapy.image)

                                putString(ADRESS,therapy.therapy.address)
                                putInt(CAPACITY, therapy.therapy.capacity)



                                //putStringSet(FOLLOWERSARRAY,user.followers)

                            }.apply()


                        }




                    }

                    override fun onFailure(call: Call<TherapyResponse>, t: Throwable) {
                        Log.e("ADD-EVENT", t.message!!, t)
                    }
                })}}}}
