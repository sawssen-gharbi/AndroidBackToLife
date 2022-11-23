package com.example.backtolife.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.backtolife.API.UserApi
import com.example.backtolife.FULLNAME
import com.example.backtolife.PREF_NAME
import com.example.backtolife.R
import com.example.backtolife.SplashScreen
import com.example.backtolife.models.ReportResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


const val DATE = "DATE"
const val MOOD = "MOOD"
const val DEPRESSEDMOOD = "DEPRESSEDMOOD"
const val ELEVATEDMOOD = "ELEVATEDMOOD"
const val IRRITABILITYMOOD = "IRRITABILITYMOOD"
const val SYMPTOMS = "SYMPTOMS"
class HomeFragment : Fragment() {
    val apiInterface = UserApi.create()
    private lateinit var mSharedPref: SharedPreferences
    private lateinit var mDatePickerBtn: Button

    private lateinit var textSelectedDate: TextView
    private lateinit var btnAdd: Button
    private lateinit var btnSignOut: Button

    private lateinit var imageHappy: ImageView
    private lateinit var imageCalm: ImageView
    private lateinit var imageManic: ImageView
    private lateinit var imageAngry: ImageView
    private lateinit var imageSad: ImageView


    private lateinit var textHappy: TextView
    private lateinit var textCalm: TextView
    private lateinit var textManic: TextView
    private lateinit var textAngry: TextView
    private lateinit var textSad: TextView

    private lateinit var sDepressed: SeekBar
    private lateinit var sElevated: SeekBar
    private lateinit var sIrritability: SeekBar


    private lateinit var btnPSNo: Button
    private lateinit var btnPSYes: Button

    lateinit var textViewName: TextView


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        refresh(context)
        return inflater.inflate(R.layout.fragment_home, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSharedPref =
            requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        val textName = mSharedPref.getString(FULLNAME, "").toString()
        textViewName = view.findViewById(R.id.textViewName)
        textViewName.text = textName

        btnAdd = view.findViewById(R.id.buttonAdd)

        mDatePickerBtn = view.findViewById(R.id.textButtonDate)
        textSelectedDate = view.findViewById(R.id.textViewSelectedDate)

        imageHappy = view.findViewById(R.id.imageViewHappy)
        textHappy = view.findViewById(R.id.textViewHappy)

        imageCalm = view.findViewById(R.id.imageViewCalm)
        textCalm = view.findViewById(R.id.textViewCalm)

        imageManic = view.findViewById(R.id.imageViewManic)
        textManic = view.findViewById(R.id.textViewManic)

        imageAngry = view.findViewById(R.id.imageViewAngry)
        textAngry = view.findViewById(R.id.textViewAngry)

        imageSad = view.findViewById(R.id.imageViewSad)
        textSad = view.findViewById(R.id.textViewSad)

        sDepressed = view.findViewById(R.id.sliderDepressed)
        sElevated = view.findViewById(R.id.sliderElevated)
        sIrritability = view.findViewById(R.id.sliderIrritability)

        btnPSNo = view.findViewById(R.id.btnPsychoticSymptomsNo)
        btnPSYes = view.findViewById(R.id.PsychoticSymptomsYes)

        btnSignOut = view.findViewById(R.id.buttonSignOut)

        //Google Sign In

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val gsc = GoogleSignIn.getClient(view.context, gso);
        val acct = GoogleSignIn.getLastSignedInAccount(view.context)
        if (acct != null) {
            val fullName = acct.displayName
            val email = acct.email
            textViewName.text = fullName

        }

        fun signOut() {
            gsc.signOut().addOnCompleteListener {
                activity?.finish()
                val intent = Intent(activity, SplashScreen::class.java)
                startActivity(intent)
            }
        }

        btnSignOut.setOnClickListener {
            signOut()
        }

    val calender = Calendar.getInstance()

    fun updateTable(c: Calendar) {
        val mf = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(mf, Locale.FRANCE)
        textSelectedDate.setText(sdf.format(c.time))

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


        val apiInterface = UserApi.create()
        val map: HashMap<String, String> = HashMap()


        imageHappy.setOnClickListener {

            map["mood"] = textHappy.text.toString()

        }

        imageCalm.setOnClickListener {
            map["mood"] = textCalm.text.toString()

        }

        imageManic.setOnClickListener {
            map["mood"] = textManic.text.toString()
        }

            imageAngry.setOnClickListener {
                map["mood"] = textAngry.text.toString()
            }

            imageSad.setOnClickListener {
                map["mood"] = textSad.text.toString()

            }

        btnPSYes.setOnClickListener{
            map["symptoms"] = btnPSYes.text.toString()
        }
        btnPSNo.setOnClickListener{
            map["symptoms"] = btnPSNo.text.toString()
        }



            btnAdd.setOnClickListener {



                map["date"] = textSelectedDate.text.toString()
                map["depressedMood"] = sDepressed.progress.toString()
                map["elevatedMood"] = sElevated.progress.toString()
                map["irritabilityMood"] = sIrritability.progress.toString()

                CoroutineScope(Dispatchers.IO).launch {
                    apiInterface.addReport(map).enqueue(object : Callback<ReportResponse> {
                        override fun onResponse(
                            call: Call<ReportResponse>, response:
                            Response<ReportResponse>
                        ) {

                            val report = response.body()
                            Log.e("success: ", report.toString())
                            if (report != null) {
                                mSharedPref.edit().apply {
                                    putString(DATE, report.report.date)
                                    putString(MOOD, report.report.mood)
                                    putString(DEPRESSEDMOOD, report.report.depressedMood.toString())
                                    putString(ELEVATEDMOOD, report.report.elevatedMood.toString())
                                    putString(
                                        IRRITABILITYMOOD,
                                        report.report.irritabilityMood.toString()
                                    )

                                }.apply()


                            }
                        }


                        override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                            println("messin")
                        }
                    })
                }
            }




            super.onViewCreated(view, savedInstanceState)
        }
    }


    fun refresh(context: Context?) {
        context?.let {
            val fragementManager = (context as? AppCompatActivity)?.supportFragmentManager
            fragementManager?.let {
                val currentFragement = fragementManager.findFragmentById(R.id.itemHome)
                currentFragement?.let {
                    val fragmentTransaction = fragementManager.beginTransaction()
                    fragmentTransaction.detach(it)
                    fragmentTransaction.attach(it)
                    fragmentTransaction.commit()
                }
            }
        }
    }



fun isValide(): Boolean {
    return true
}




