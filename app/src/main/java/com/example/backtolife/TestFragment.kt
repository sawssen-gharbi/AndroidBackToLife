package com.example.backtolife

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker
import java.text.SimpleDateFormat
import java.util.*


class TestFragment : Fragment() {
    lateinit var dayDatePicker : DayScrollDatePicker
     lateinit var SelectedDate : String

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment

        val rootview =  inflater.inflate(R.layout.fragment_test, container, false)

            dayDatePicker = rootview.findViewById(R.id.dayDatePicker)
            dayDatePicker.setStartDate(1, 11, 2022)
            dayDatePicker.getSelectedDate {

                SelectedDate = it.toString()
                Toast.makeText(context, SelectedDate, Toast.LENGTH_SHORT).show()
            }

















        return rootview

    }


}