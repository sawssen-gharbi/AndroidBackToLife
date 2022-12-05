package com.example.backtolife

import ListPatientAdapter
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import com.example.backtolife.API.UserApi
import java.util.*





class ListPatient : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var adapter:ListPatientAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootview = inflater.inflate(R.layout.fragment_list_patient, container, false);
        constraintLayout = rootview.findViewById(R.id.layout2);
        animationDrawable = constraintLayout.getBackground() as AnimationDrawable;
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

       return rootview
    }
}