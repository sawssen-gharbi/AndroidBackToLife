package com.example.backtolife

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.backtolife.API.UserApi
import com.example.backtolife.Adapter.ListTherapy
import com.example.backtolife.models.SwipeToDeleteCallBack
import com.example.backtolife.models.Therapy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var mSharedPref: SharedPreferences
private lateinit var searchProductsBar: EditText
class listTherapy : Fragment() {
    private lateinit var recyclerView: RecyclerView


    val apiInterface = UserApi.create()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_event_doctor, container, false)
        searchProductsBar=rootview.findViewById(R.id.searchProductsBar)



        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);

        val id: String? =mSharedPref.getString(ID, "")
        apiInterface.getAll().enqueue(object :
            Callback<List<Therapy>> {
            override fun onResponse(
                call: Call<List<Therapy>>, response:
                Response<List<Therapy>>
            ) {

                if (response.isSuccessful) {
                    recyclerView = rootview.findViewById(R.id.recycleDoctor)
                    val adapter = ListTherapy(response.body()!! as MutableList<Therapy>)
                    for(t: Therapy in response.body()!!){
                        Log.e("Therapy response : ",t.titre)
                    }

                    recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,
                        false)
                    recyclerView.adapter = adapter
                    val swipeDelete = object : SwipeToDeleteCallBack(requireContext()) {
                        override fun onSwiped(
                            viewHolder: RecyclerView.ViewHolder,
                            direction: Int
                        ) {
                            val builder = AlertDialog.Builder(requireContext())
                            builder.setTitle("Delete Item")
                            builder.setMessage("Are you sure you want to delete item")
                            builder.setPositiveButton("Confirm") { dialog, which ->
                                apiInterface.deleteTherapy(mSharedPref.getString(IDTHERAPY, "").toString())
                                    .enqueue(object: Callback<Therapy> {
                                        override fun onResponse(call: Call<Therapy>, response: Response<Therapy>)
                                        {
                                            if (response.isSuccessful){
                                                Log.i("therapy Deleted", response.body().toString())
                                            }
                                        }

                                        override fun onFailure(call: Call<Therapy>, t: Throwable)
                                        {
                                            println("okay")
                                        }
                                    })
                            }

                            builder.setNegativeButton("Cancel") { dialog, which ->
                                val position = viewHolder.adapterPosition
                                adapter.notifyItemChanged(position)
                            }
                            builder.show()
                        }

                    }


                    val touchHelper = ItemTouchHelper(swipeDelete)
                    touchHelper.attachToRecyclerView(recyclerView)
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