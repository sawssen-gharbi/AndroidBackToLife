package com.example.backtolife.fragments

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.backtolife.*
import com.example.backtolife.API.UserApi
import com.example.backtolife.models.User
import com.example.studentchat.Interface.RealPathUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.HashMap
const val IMAGEU = "IMAGEU"


class ProfileFragment : Fragment() {
    private lateinit var fullName: TextInputLayout
    lateinit var email: TextInputLayout

    private lateinit var btnupload : Button
    private  var btnModifier : Button? = null
    private lateinit var mSharedPref: SharedPreferences


    lateinit var image: ImageView
    var multipartImage: MultipartBody.Part? = null

    private var selectedImageUri: Uri? = null
    private  lateinit var path2:String





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(/* requestCode = */ requestCode, /* resultCode = */
            resultCode, /* data = */
            data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_CODE) {
            val rl= RealPathUtil()
            val p: String? = data?.data?.let { this.context?.let { it1 -> rl.getRealPath(it1, it) } }
            if(!p.isNullOrEmpty()){
                path2=p;
            }
            image.setImageURI(data?.data)
            selectedImageUri= Uri.parse(data?.dataString!!)
        }


    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMS_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mSharedPref.edit().putBoolean(IS_GRANTED_READ_IMAGES, true).apply()
            } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fullName = view.findViewById(R.id.textInputLayoutFullNameProfile1)
        email = view.findViewById(R.id.textInputLayoutUsernameProfile1)
        mSharedPref =
            requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        btnModifier = view.findViewById(R.id.button4)
        btnupload = view.findViewById(R.id.button1)
        fullName.editText!!.setText(mSharedPref.getString(FULLNAME, "").toString())
        email.editText!!.setText(mSharedPref.getString(EMAIL, "").toString())



        requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            PERMS_REQUEST_CODE);
        image = view.findViewById(R.id.imageView2)
        image.setOnClickListener {startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE_CODE) }

        super.onViewCreated(view, savedInstanceState)


        btnModifier!!.setOnClickListener {
            doUpload()


        }
        btnupload.setOnClickListener{
            uploadphoto()
        }



    }
    private fun uploadphoto(){


        val f= File(path2)
        val reqFile: RequestBody = RequestBody.create("image/${f.extension}".toMediaTypeOrNull(), f)
        val body: MultipartBody.Part= MultipartBody.Part.createFormData("image",f.name,reqFile)
        val id=mSharedPref.getString(ID,"")


        val apiInterface = UserApi.create()



        if (body != null) {
            apiInterface.uploadPhoto(id,body)
                .enqueue(object : Callback<User> {
                    override fun onResponse(
                        call: Call<User>,
                        response: Response<User>
                    ) {
                        if (response.isSuccessful) {
                            mSharedPref.edit().apply {
                                putString(
                                   IMAGEU,
                                    response.body()?.photo.toString()
                                )
                            }.apply()
                            println(response.body().toString())
                            Toast.makeText(
                                requireContext(),
                                "profile picture updated",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            println(response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.i("error: ", "error")
                    }
                })
        }}

    private fun doUpload() {
        val map: HashMap<String, String> = HashMap()

        map["fullName"] = fullName.editText?.text.toString()

        map["email"] = email.editText?.text.toString()





        CoroutineScope(Dispatchers.IO).launch {
            apiInterface.editProfile(mSharedPref.getString(ID, "").toString(), map)
                .enqueue(object :
                    Callback<User> {
                    // ija discord hhh
                    override fun onResponse(
                        call: Call<User>,
                        response:
                        Response<User>
                    ) {
                        val user = response.body()
                        Log.e("success: ", user.toString())
                        if (user != null) {
                            mSharedPref.edit().apply {
                                putString(ID, user._id)
                                putString(FULLNAME, user.fullName)
                                putString(EMAIL, "No Email")

                            }.apply()

                        } else {
                            Toast.makeText(requireContext(), "error!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(requireContext(),
                            "Connexion error!",
                            Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
    private fun showAlertDialog(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Logout")
            .setMessage("are you sure you want to logout?")
            .setPositiveButton("Ok") {dialog, which ->
                mSharedPref.edit().clear().apply()

                requireActivity().finish()
                val mainIntent = Intent(requireContext(), Login ::class.java)
                startActivity(mainIntent)
            }
            .setNegativeButton("Cancel") {dialog, which ->

            }
            .show()
    }

}




