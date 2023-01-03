package com.example.backtolife.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.backtolife.API.UserApi
import com.example.backtolife.Adapter.ListEventAdap
import com.example.backtolife.Adapter.ListTherapy
import com.example.backtolife.ID
import com.example.backtolife.PREF_NAME
import com.example.backtolife.R
import com.example.backtolife.models.SwipeToDeleteCallBack
import com.example.backtolife.models.Therapy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var mSharedPref: SharedPreferences
private lateinit var searchProductsBar: EditText
private lateinit var toolbar: androidx.appcompat.widget.Toolbar
private lateinit var search: ImageView

class listTherapy : Fragment() {
    private lateinit var recyclerView: RecyclerView


    val apiInterface = UserApi.create()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_list_therapy, container, false)
        searchProductsBar=rootview.findViewById(R.id.searchProductsBar)

        toolbar=rootview.findViewById(R.id.tool1)
        search = rootview.findViewById(R.id.serchhh)




        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);

        val id: String? =mSharedPref.getString(ID, "")
        apiInterface.getAll().enqueue(object :
            Callback<List<Therapy>> {
            override fun onResponse(
                call: Call<List<Therapy>>, response:
                Response<List<Therapy>>
            ) {

                if (response.isSuccessful) {
                    recyclerView = rootview.findViewById(R.id.recycleTherapy)
                    val adapter = ListTherapy(response.body()!! as MutableList<Therapy>)
                    search.setOnClickListener{
                        val searchh=searchProductsBar.text.toString()
                        adapter.filter.filter(searchh)

                    }
                    for(t: Therapy in response.body()!!){
                        Log.e("Therapy response : ",t.titre)
                    }

                    recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,
                        false)
                    recyclerView.adapter = adapter



                }


                /*    searchProductsBar.on(IME_ACTION_DONE) {
                        searchProductsBar.clearFocus()
                        searchProductsBar.hideKeyboard()

                        var filtered =  adapter.therapies.filter { p ->
                            p.titre == searchProductsBar.text.toString()
                        }.toMutableList()
                    }*/

            }





            override fun onFailure(call: Call<List<Therapy>>, t: Throwable) {
                Log.e("gg",t.message.toString())
            }
        })


        // Inflate the layout for this fragment

        return rootview

    }
}