package com.example.backtolife

import ListPatientAdapter
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.backtolife.API.UserApi
import com.example.backtolife.Adapter.ListTherapy
import com.example.backtolife.models.SwipeToDeleteCallBack
import com.example.backtolife.models.Therapy
import com.example.backtolife.models.reservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

private lateinit var mSharedPref: SharedPreferences






class ListPatient() : Fragment() {


    val apiInterface = UserApi.create()


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:ListPatientAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootview = inflater.inflate(R.layout.fragment_list_patient, container, false);
        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);

            val body: String? =mSharedPref.getString(ID, "")
        apiInterface.getAllR(body).enqueue(object :
            Callback<List<reservation>> {
            override fun onResponse(
                call: Call<List<reservation>>, response:
                Response<List<reservation>>
            ) {

                if (response.isSuccessful) {
                    recyclerView = rootview.findViewById(R.id.recycler_inv)
                    val adapter = ListPatientAdapter(response.body()!! as ArrayList<reservation>)
                    for (t: reservation in response.body()!!) {
                        Log.e("Therapy response : ", t._id)
                    }

                    recyclerView.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL,
                            false)
                    recyclerView.adapter = adapter


                    /*    searchProductsBar.on(IME_ACTION_DONE) {
                        searchProductsBar.clearFocus()
                        searchProductsBar.hideKeyboard()

                        var filtered =  adapter.therapies.filter { p ->
                            p.titre == searchProductsBar.text.toString()
                        }.toMutableList()
                    }*/

                }}





            override fun onFailure(call: Call<List<reservation>>, t: Throwable) {
                Log.e("gg",t.message.toString())
            }
        })


        // Inflate the layout for this fragment

        return rootview

    }
}