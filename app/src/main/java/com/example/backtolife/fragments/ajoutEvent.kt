package com.example.backtolife.fragments

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
import com.example.backtolife.models.TherapyResponse
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
import android.widget.TextView
import com.example.backtolife.*
import com.example.studentchat.Interface.RealPathUtil
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.MediaType.Companion.get
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

const val IDTHERAPY = "IDTHERAPY"
const val TITRE = "TITRE"
const val CAPACITY = "CAPACITY"
const val ADRESS = "ADDRESS"
const val STATUS="status"
const val IMAGE = "IMAGE"


@Suppress("OverridingDeprecatedMember", "DEPRECATION")
class ajoutEvent : Fragment() {
   // lateinit var mDatePickerBtn : Button
  private  lateinit var titre : TextInputLayout
    private lateinit var date : Button
    private  lateinit var capacity : TextInputLayout
    private  lateinit var str1:String

    private lateinit var butLoc:Button

    private  lateinit var textSelectedDate:TextView
    private  var imageU :Uri?=null
    private lateinit var address:TextInputLayout
    private lateinit var image: ImageView
    private  var multipartImage: MultipartBody.Part? = null
    private  lateinit var path:String



    private lateinit var mSharedPref: SharedPreferences

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(/* requestCode = */ requestCode, /* resultCode = */
            resultCode, /* data = */
            data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE) {
            val rl=RealPathUtil()
            val p: String? = data?.data?.let { this.context?.let { it1 -> rl.getRealPath(it1, it) } }
            if(!p.isNullOrEmpty()){
                path=p;
            }
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
       titre=view.findViewById(R.id.textInputLayoutFullNameProfile)
       // date=view.findViewById(R.id.mtrl_picker_text_input_date)
        date =view.findViewById<Button>(R.id.textButtonDate)
        address=view.findViewById(R.id.textInputLayoutUsernameProfile)
        textSelectedDate=view.findViewById(R.id.textViewSelectedDate)
        butLoc=view.findViewById(R.id.map)

        butLoc.setOnClickListener{
            val intent = Intent(requireContext(), shippingAddress::class.java)
            startActivity(intent)
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
            DatePickerDialog(view.context,datepicker,calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH)).show()

        }




        capacity=view.findViewById(R.id.textInputPhoneProfile)


        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),PERMS_REQUEST_CODE);
        image = view.findViewById(R.id.lottie)
        image.setOnClickListener {startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE_CODE) }

        super.onViewCreated(view, savedInstanceState)

       val bot = view.findViewById<Button>(R.id.button3)
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



            Log.e("path",path)
            val f=File(path)
            val reqFile:RequestBody= f.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val body: MultipartBody.Part=MultipartBody.Part.createFormData("image",f.name,reqFile)
            mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
            var address1 = mSharedPref.getString(ADDRESS,"")
            Log.e("address here ",address1.toString())
            address.editText!!.setText(address1.toString())

           // val reqFile = f.asRequestBody("image/jpeg".toMediaTypeOrNull())
            //val body: MultipartBody.Part=MultipartBody.Part.createFormData("image",f.name,reqFile)

            val map: HashMap<String, RequestBody> = HashMap()
            map["date"] =   textSelectedDate.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            map["titre"] = titre.editText?.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            map["capacity"] = capacity.editText?.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            map["address"]=address.editText?.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())

            //map["image"]= body

            CoroutineScope(Dispatchers.IO).launch {
                apiInterface.addOnce(map, body, mSharedPref.getString(ID, "")!!).enqueue(object : Callback<TherapyResponse> {
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
                            Toast.makeText( activity,"yes", Toast.LENGTH_SHORT).show()


                        }




    }

                    override fun onFailure(call: Call<TherapyResponse>, t: Throwable) {
                        Log.e("ADD-EVENT", t.message!!, t)
                    }
                })}}}
    private fun isValide(): Boolean {
        if ((titre.editText?.text.toString()).isEmpty()){
            titre.error = "title cannot be empty"
            return false
        }

        if ((capacity.editText?.text.toString()).isEmpty()){
            capacity.error = "capacity cannot be empty"
            return false
        }else {
            capacity.error = null
        }
        if (address==null){
            address.error = "address cannot be empty"
            return false
        }else {
            address.error = null
        }
        if(path==null){
            Toast.makeText( activity,"image cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }


        if ((date.text.toString()).isEmpty()){
            date.error = "capacity cannot be empty"
            return false
        }else {
            date.error = null
        }


        return true
    }}



