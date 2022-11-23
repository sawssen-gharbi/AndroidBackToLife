package com.example.backtolife.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.backtolife.API.UserApi
import com.example.backtolife.Adapter.MyReportAdapter
import com.example.backtolife.FULLNAME
import com.example.backtolife.ID
import com.example.backtolife.PREF_NAME

import com.example.backtolife.R
import com.example.backtolife.models.Report
import com.example.backtolife.models.Reports
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var mSharedPref: SharedPreferences
val apiInterface = UserApi.create()

class ReportFragment : Fragment(R.layout.fragment_report) {

    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootview = inflater.inflate(R.layout.fragment_report, container, false)

        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);


        apiInterface.getReport(mSharedPref.getString(ID, "").toString()).enqueue(object : Callback<List<Report>> {
                override fun onResponse(
                    call: Call<List<Report>>, response:
                    Response<List<Report>>
                ) {

                    if (response.isSuccessful) {
                        recyclerView = rootview.findViewById(R.id.recycleViewReport)
                        val adapter = MyReportAdapter(response.body()!! as MutableList<Report>)

                        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,
                            false)
                        recyclerView.adapter = adapter

                    }
                }




            override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                Log.e("gg","g")
            }
        })


        // Inflate the layout for this fragment

        return rootview

    }
}



   /* private fun dataInitial(){
        reportArrayList = arrayListOf<Reports>()
        imageId = arrayOf(
            R.drawable.angryy,
            R.drawable.happyy

        )

        mood = arrayOf(
            getString(R.string.angry),
            getString(R.string.happy)
        )
        date = arrayOf(
            getString(R.string.date),
            getString(R.string.date)
        )
        depressedPourcentage = arrayOf(
            getString(R.string.depressed),
            getString(R.string.depressed)
        )
        elevatedPourcentage = arrayOf(
            getString(R.string.elevated),
            getString(R.string.elevated)

        )
        irritabilityPourcentage = arrayOf(
            getString(R.string.irritability),
            getString(R.string.irritability)
        )
        psychoticSymptoms = arrayOf(
            getString(R.string.psychotic),
            getString(R.string.psychotic)
        )


        for ( i in imageId.indices){

            val  reports = Reports(imageId[i], mood[i],date[i],depressedPourcentage[i],
                elevatedPourcentage[i],irritabilityPourcentage[i],psychoticSymptoms[i])

            reportArrayList.add(reports)
        }*/






