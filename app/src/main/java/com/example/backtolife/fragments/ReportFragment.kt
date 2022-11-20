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

import com.example.backtolife.R
import com.example.backtolife.models.Reports

class ReportFragment : Fragment() {

    private lateinit var adapter : MyReportAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var reportArrayList : ArrayList<Reports>


    lateinit var imageId : Array<Int>
    lateinit var mood : Array<String>
    lateinit var date : Array<String>
    lateinit var depressedPourcentage: Array<String>
    lateinit var elevatedPourcentage: Array<String>
    lateinit var irritabilityPourcentage: Array<String>
    lateinit var psychoticSymptoms: Array<String>
    lateinit var reports : Array<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_report, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (super.onViewCreated(view, savedInstanceState))
        dataInitial()
        val layoutManager = LinearLayoutManager(context)

        recyclerView = view.findViewById(R.id.recycleViewReport)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = MyReportAdapter(reportArrayList)
        recyclerView.adapter = adapter



    }


    private fun dataInitial(){
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
        }



    }


}