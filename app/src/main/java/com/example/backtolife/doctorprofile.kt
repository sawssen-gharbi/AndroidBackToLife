package com.example.backtolife

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import com.google.android.material.textfield.TextInputLayout
import okhttp3.MediaType.Companion.toMediaType

class doctorprofile : Fragment() {








    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val rootview=inflater.inflate(R.layout.fragment_doctorprofile, container, false)


        return rootview
    }


}