package com.example.backtolife

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.backtolife.API.UserApi
import com.example.backtolife.models.LoginResponse
import com.example.backtolife.models.TherapyResponse
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Context.MODE_PRIVATE
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore

import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.internal.format
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

const val TITRE = "TITRE"
const val DATE = "DATE"
const val CAPACITY = "CAPACITY"
const val PICK_IMAGE_CODE = 100
const val PERMS_REQUEST_CODE = 101
const val IS_GRANTED_READ_IMAGES = "IS_GRANTED_READ_IMAGES"
const val PREFS_NAME = "APP_PREFS"

@Suppress("DEPRECATION")
class ajoutEvent : Fragment() {
    lateinit var mDatePickerBtn : Button
    lateinit var titre : TextInputLayout
    lateinit var date : TextInputLayout
    lateinit var capacity : TextInputLayout
    private  var imageU :Uri?=null
    private lateinit var image: ImageView
    var multipartImage: MultipartBody.Part? = null



    private lateinit var mSharedPref: SharedPreferences
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(/* requestCode = */ requestCode, /* resultCode = */
            resultCode, /* data = */
            data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE) {
            image.setImageURI(data?.data)
        imageU= Uri.parse(data?.dataString!!)
        }


    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMS_REQUEST_CODE ->if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {mSharedPref.edit().putBoolean(IS_GRANTED_READ_IMAGES, true).apply()}
            else { super.onRequestPermissionsResult(requestCode, permissions, grantResults)}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        refresh(context)
        return inflater.inflate(R.layout.fragment_ajout_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       titre=view.findViewById(R.id.textInputLayoutFullName)
        date=view.findViewById(R.id.mtrl_picker_text_input_date)
        mDatePickerBtn =view.findViewById<Button>(R.id.textButtonDate)


        val calender = Calendar.getInstance()
        fun updateTable(c: Calendar) {
            val mf = SimpleDateFormat("dd-MM-yyyy")
            val sdf = mf.format(c.time)
            mDatePickerBtn.text = sdf
        }

        val datepicker = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, month)
            calender.set(Calendar.DAY_OF_MONTH, day)

            updateTable(calender)

        }




        mDatePickerBtn.setOnClickListener {
            DatePickerDialog(view.context,datepicker,calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH)).show()

        }




        capacity=view.findViewById(R.id.nembre)


        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),PERMS_REQUEST_CODE);
        image = view.findViewById(R.id.imageView3)
        image.setOnClickListener {startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE_CODE) }

        super.onViewCreated(view, savedInstanceState)

       val bot = view.findViewById<Button>(R.id.butajout)
        bot.setOnClickListener {
            doAjout()

        }
    }
    fun refresh(context: Context?)
    {
        context?.let {
            val fragementManager = (context as? AppCompatActivity)?.supportFragmentManager
            fragementManager?.let {
                val currentFragement = fragementManager.findFragmentById(R.id.homeDoctor)
                currentFragement?.let {
                    val fragmentTransaction = fragementManager.beginTransaction()
                    fragmentTransaction.detach(it)
                    fragmentTransaction.attach(it)
                    fragmentTransaction.commit()
                }
            }
        }
    }

    private fun doAjout() {
        if (isValide()){
            val apiInterface = UserApi.create()
            val file = FileProvider().openFile(imageU!!,"r").let {
                it;
            }
            Log.i("FILE-INFO", file.toString())
            val requestFile: RequestBody = RequestBody.create(
                "multipart/form-data".toMediaType(),
                file
            )
           multipartImage =
                MultipartBody.Part.createFormData("image", file, requestFile);

            Log.e("image", file.toString())


            val map: HashMap<String, RequestBody> = HashMap()
            map["date"] =  mDatePickerBtn.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            map["titre"] = titre.editText?.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            map["capacity"] = capacity.editText?.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())

            CoroutineScope(Dispatchers.IO).launch {
                apiInterface.addOnce(map, multipartImage!!, mSharedPref.getString(ID, "")!!).enqueue(object : Callback<TherapyResponse> {
// ija discord hhh
                    override fun onResponse(call: Call<TherapyResponse>, response:
                    Response<TherapyResponse>
                    ) {
                        val therapy = response.body()
                        Log.e("success: ", therapy.toString())
                        if (therapy != null) {
                            mSharedPref.edit().apply {
                                putString(ID, therapy.therapy._id)
                                putString(TITRE, therapy.therapy.titre)
                                putString(DATE, therapy.therapy.date)
                                putInt(CAPACITY, therapy.therapy.capacity)



                                //putStringSet(FOLLOWERSARRAY,user.followers)

                            }.apply()
                            Toast.makeText( activity,"yes", Toast.LENGTH_SHORT).show()


                        }




    }

                    override fun onFailure(call: Call<TherapyResponse>, t: Throwable) {
                        Log.e("ADD-EVENT", t.message!!, t)
                    }
                })}}}}



