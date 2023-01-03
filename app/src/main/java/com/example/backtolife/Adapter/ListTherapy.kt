package com.example.backtolife.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.backtolife.*
import com.example.backtolife.API.UserApi
import com.example.backtolife.models.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.format.DateTimeFormatter

class ListTherapy(val therapies: MutableList<Therapy>):RecyclerView.Adapter<ListTherapy.ViewHolder>(),Filterable {
    val apiInterface = UserApi.create()
    lateinit var msharedPref: SharedPreferences
    var mContext: Context? = null
    var therapisFilteredList: List<Therapy> = ArrayList()
    init {
        therapisFilteredList=therapies
    }
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
        val status=msharedPref.getString(STATUS,"")
        Log.e("status",status.toString())


        holder.dontsent.setOnClickListener {
            apiInterface.deleteReservation(therapies.removeAt(position)._id)
                .enqueue(object : Callback<ReservationItem> {
                    override fun onResponse(
                        call: Call<ReservationItem>,
                        response: Response<ReservationItem>
                    ) {
                        if (response.isSuccessful) {
                            Log.e("deleted", "deleted :)")

                        }

                    }

                    override fun onFailure(call: Call<ReservationItem>, t: Throwable) {

                    }


                })
            notifyItemRemoved(position)
        }
        //convert date type
        var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")

        Glide.with(holder.itemView).load(data.image).into(holder.imgUser)
        holder.name.text = data.titre
        holder.capacity.text = data.capacity.toString()
        holder.address.text=data.address.toString()
        holder.time.text = data.date.format(formatter)
        Log.e("image path :", data.image)
        holder.sent.setOnClickListener {

            Log.e("", data._id)


            val map = HashMap<String, String>()
            map.put("id", data._id);

            val apiInterface = UserApi.create()
            apiInterface.sendInvitation(map).enqueue(object : Callback<ServerResponse> {
                override fun onResponse(
                    call: Call<ServerResponse>,
                    response: Response<ServerResponse>
                ) {
                    if(response.isSuccessful){
                        holder.sent.visibility=View.GONE
                        holder.dontsent.visibility=View.VISIBLE
                    }else{
                        holder.sent.visibility=View.VISIBLE
                        holder.dontsent.visibility=View.GONE
                    }

                }

                override fun onFailure(call: Call<ServerResponse>, t: Throwable) {

                }


            })


            Log.e("you reserrrrrveeeeeee", data._id)



        }}

    override fun getItemCount(): Int = therapies.size;

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.name_user);
        val capacity = view.findViewById<TextView>(R.id.capacity);
        val imgUser = view.findViewById<ImageView>(R.id.img_post);
        val time = view.findViewById<TextView>(R.id.time1);
        val address=view.findViewById<TextView>(R.id.location1);
        val sent = view.findViewById<Button>(R.id.reserve);
        val dontsent = view.findViewById<Button>(R.id.reserve_annuler);



    }

    private fun sentInvitation(_id: String, position: Int) {

    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var charSearch = constraint.toString()
                if(charSearch.isEmpty()){
                    therapisFilteredList=therapies
                } else {
                    var reslutList= ArrayList<Therapy>()
                    for (therap in therapies){
                        if(therap.titre.lowercase().contains(charSearch.lowercase())){
                            reslutList.add(therap)
                        }
                    }
                    therapisFilteredList=reslutList
                }
                val filterResults=FilterResults()
                filterResults.values=therapisFilteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                therapisFilteredList = results?.values as ArrayList<Therapy>
                notifyDataSetChanged()
            }

        }
    }


}
