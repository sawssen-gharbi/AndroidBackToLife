package com.example.backtolife.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import com.example.backtolife.R
import com.google.android.material.datepicker.MaterialDatePicker


class HomeFragment : Fragment() {
    private lateinit var mDatePickerBtn: Button
    private lateinit var textSelectedDate: TextView


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment



        val rootview = inflater.inflate(R.layout.fragment_home, container, false)

        mDatePickerBtn = rootview.findViewById(R.id.textButtonDate)
        textSelectedDate = rootview.findViewById(R.id.textViewSelectedDate)

        //Material Date Picker
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date of today")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        mDatePickerBtn.setOnClickListener {
            datePicker.show(parentFragmentManager, "Date_Picker")
        }
        datePicker.addOnPositiveButtonClickListener {

            textSelectedDate.text = datePicker.headerText
        }






        return rootview
    }




}