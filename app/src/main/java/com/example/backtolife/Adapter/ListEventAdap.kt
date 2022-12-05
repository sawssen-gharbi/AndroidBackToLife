package com.example.backtolife.Adapter


import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.backtolife.R
import com.example.backtolife.models.Therapy
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter


class ListEventAdap(var therapies: MutableList<Therapy>) :RecyclerView.Adapter<ListEventAdap.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_doctor,parent,false)
        return ListEventAdap.ViewHolder(view);
    }



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val data: Therapy = therapies[position];


       //convert date type
        var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")

       Glide.with(holder.itemView).load(data.image).into(holder.imgUser)
       holder.name.text=data.titre
       holder.capacity.text=data.capacity.toString()
       holder.time.text=data.date.format(formatter)
        holder.adresse.text=data.address
       Log.e("image path :",data.image)

   }
    override fun getItemCount(): Int = therapies.size;

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val name=view.findViewById<TextView>(R.id.name_user);
        val capacity=view.findViewById<TextView>(R.id.capacity);
        val imgUser = view.findViewById<ImageView>(R.id.img_post);
        val time=view.findViewById<TextView>(R.id.time);
        val adresse=view.findViewById<TextView>(R.id.location)

    }



}
