package com.example.backtolife.fragments

import ListPatientAdapter
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.backtolife.API.UserApi
import com.example.backtolife.ID
import com.example.backtolife.PREF_NAME
import com.example.backtolife.R
import com.example.backtolife.models.ReservationItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

private lateinit var mSharedPref: SharedPreferences


class ListPatient() : Fragment() {


    val apiInterface = UserApi.create()


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListPatientAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootview = inflater.inflate(R.layout.fragment_list_patient, container, false)
        mSharedPref =
            requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        recyclerView = rootview.findViewById(R.id.recycler_inv)

        adapter = ListPatientAdapter(mutableListOf())

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        val id_doctor= mSharedPref.getString(ID,"")

        apiInterface.getAllR(id_doctor!!).enqueue(object :
            Callback<List<ReservationItem>> {
            override fun onResponse(
                call: Call<List<ReservationItem>>,
                response:
                Response<List<ReservationItem>>,
            ) {

                if (response.isSuccessful) {
                   adapter.addAll(response.body()!!.toMutableList());
                    Log.e("RESERVATION","Response body" + response.body())

                    /*    searchProductsBar.on(IME_ACTION_DONE) {
                        searchProductsBar.clearFocus()
                        searchProductsBar.hideKeyboard()

                        var filtered =  adapter.therapies.filter { p ->
                            p.titre == searchProductsBar.text.toString()
                        }.toMutableList()
                    }*/

                }
            }


            override fun onFailure(call: Call<List<ReservationItem>>, t: Throwable) {
                Log.e("gg", t.message.toString())
            }
        })


        // Inflate the layout for this fragment

        return rootview

    }
}