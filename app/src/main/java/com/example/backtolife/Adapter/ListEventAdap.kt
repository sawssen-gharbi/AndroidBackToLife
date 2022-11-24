package com.example.backtolife.Adapter

import android.view.LayoutInflater
import android.view.View


import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.backtolife.R
import com.example.backtolife.models.Therapy
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class ListEventAdap(var therapies: MutableList<Therapy>) :RecyclerView.Adapter<ListEventAdap.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder

       =  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_group,parent,false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(therapies[position])


    override fun getItemCount(): Int = therapies.size;

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        fun bind(therapy: Therapy) {

            titre?.text = therapy.titre

            date?.text = therapy.date

            capacity?.text = "${therapy.capacity}"

            Picasso.get()
                .load(therapy.image)
                .resize(300, 300)
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.angryy)
                .into(imgUser!!)
        }

        private var titre: TextView?= null
        private var date: TextView? = null
        private var imgUser: ImageView? = null
        private var capacity: TextView? = null

        init {
             titre=view.findViewById(R.id.name_user)
            date=view.findViewById(R.id.time)
            imgUser = view.findViewById(R.id.img_user)
             capacity =view.findViewById(R.id.capacity)
        }



    }



}
