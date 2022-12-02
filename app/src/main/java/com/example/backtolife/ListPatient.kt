package com.example.backtolife

import ListPatientAdapter
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.backtolife.API.UserApi
import com.example.backtolife.Adapter.ListEventAdap
import com.example.backtolife.models.Therapy
import com.example.backtolife.models.User
import com.example.backtolife.models.patient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var mSharedPref: SharedPreferences
val ApiInterface = UserApi.create()



class ListPatient : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var adapter:ListPatientAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       val rootview= inflater.inflate(R.layout.fragment_list_patient, container, false);
        constraintLayout = rootview.findViewById(R.id.layout2);
        animationDrawable = constraintLayout.getBackground() as AnimationDrawable;
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
Log.e("id",mSharedPref.getString(IDTHERAPY, "").toString())
        adapter=ListPatientAdapter(mutableListOf())

        recyclerView = rootview.findViewById(R.id.recyclePatient)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,
            false)
        recyclerView.adapter = adapter

        ApiInterface.getUser(mSharedPref.getString(IDTHERAPY, "").toString()).enqueue(object :
            Callback<List<patient>> {
            override fun onResponse(
                call: Call<List<patient>>, response:
                Response<List<patient>>
            ) {

                if (response.isSuccessful) {
                  var listt=response.body()!! as MutableList<patient>


                }
            }






            override fun onFailure(call: Call<List<patient>>, t: Throwable) {
                Log.e("gg","g")            }
        })


        // Inflate the layout for this fragment

        return rootview

    }
}