package com.example.backtolife

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.backtolife.API.UserApi
import com.example.backtolife.Adapter.ListEventAdap
import com.example.backtolife.Adapter.MyReportAdapter
import com.example.backtolife.models.LoginResponse
import com.example.backtolife.models.Therapy
import com.example.backtolife.models.doctor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private lateinit var mSharedPref: SharedPreferences
val apiInterface = UserApi.create()


class EventDoctor : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview = inflater.inflate(R.layout.fragment_event_doctor, container, false)

        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);


        apiInterface.getTherapy(mSharedPref.getString(ID, "").toString()).enqueue(object : Callback<List<Therapy>> {
            override fun onResponse(
                call: Call<List<Therapy>>, response:
                Response<List<Therapy>>
            ) {

                if (response.isSuccessful) {
                    recyclerView = rootview.findViewById(R.id.recycleDoctor)
                    val adapter = ListEventAdap(response.body()!! as MutableList<Therapy>)

                    recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,
                        false)
                    recyclerView.adapter = adapter

                }
            }




            override fun onFailure(call: Call<List<Therapy>>, t: Throwable) {
                Log.e("gg","g")
            }
        })


        // Inflate the layout for this fragment

        return rootview

    }
}