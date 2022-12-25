package com.example.backtolife.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.backtolife.*
import com.example.backtolife.API.UserApi
import com.example.backtolife.models.ServerResponse
import com.example.backtolife.models.Therapy
import com.example.backtolife.models.event

import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.format.DateTimeFormatter

class ListTherapy(val therapies: MutableList<Therapy>):RecyclerView.Adapter<ListTherapy.ViewHolder>() {
    lateinit var msharedPref: SharedPreferences
    var mContext: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_therapy, parent, false);


        this.mContext = parent.context
        return ListTherapy.ViewHolder(view);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: Therapy = therapies[position];

        msharedPref = mContext?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)!!
        //convert date type
        var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")

        Glide.with(holder.itemView).load(data.image).into(holder.imgUser)
        holder.name.text = data.titre
        holder.capacity.text = data.capacity.toString()
        holder.time.text = data.date.format(formatter)
        Log.e("image path :", data.image)
        holder.sent.setOnClickListener {

            Log.e("", data._id)


            sentInvitation(data._id, position)
            Log.e("you reserrrrrveeeeeee", data._id)



        }}

    override fun getItemCount(): Int = therapies.size;

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.name_user);
        val capacity = view.findViewById<TextView>(R.id.capacity);
        val imgUser = view.findViewById<ImageView>(R.id.img_post);
        val time = view.findViewById<TextView>(R.id.time);
        val sent = view.findViewById<Button>(R.id.reserve);


    }

    private fun sentInvitation(_id: String, position: Int) {

        val map = HashMap<String, String>()
        map.put("id", _id);

        val apiInterface = UserApi.create()
        apiInterface.sendInvitation(map).enqueue(object : Callback<ServerResponse> {
            override fun onResponse(
                call: Call<ServerResponse>,
                response: Response<ServerResponse>
            ) {


                if (response.isSuccessful) {
                    Log.e("t", therapies[position].dispo.toString())
                    if (therapies[position].dispo) {
                        apiInterface.deleteTherapy(therapies.removeAt(position)._id)
                            .enqueue(object : Callback<Therapy> {
                                override fun onResponse(
                                    call: Call<Therapy>,
                                    response: Response<Therapy>
                                ) {
                                    if (response.isSuccessful) {
                                        Log.e("deleted", "deleted :)")

                                    }

                                }

                                override fun onFailure(call: Call<Therapy>, t: Throwable) {

                                }


                            })
                        notifyItemRemoved(position)

                    }else {
                        Toast.makeText(mContext, "not available", Toast.LENGTH_SHORT).show()
                    }  }}

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {

            }


        })

    }
}
