package com.example.backtolife.Adapter

import android.view.LayoutInflater
import android.view.View


import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.backtolife.R
import com.example.backtolife.models.doctor

import de.hdodenhof.circleimageview.CircleImageView

class ListEvent(val listEvent:ArrayList<doctor>):RecyclerView.Adapter<ListEvent.ViewHolder>() {
  var onItemOnClick: ((doctor)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_group,parent,false);
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doctor: doctor =listEvent[position]
        holder.title.text=doctor.title
        holder.capacity.text=doctor.capacity;
        holder.date.text=doctor.date;
        holder.img.setImageResource(doctor.img);
        holder.itemView.setOnClickListener{
            onItemOnClick?.invoke(doctor)
        }    }



    override fun getItemCount(): Int {
        return listEvent.size;
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val title=view.findViewById<TextView>(R.id.name_user);
        val img=view.findViewById<CircleImageView>(R.id.img_user);
        val date=view.findViewById<TextView>(R.id.time);
        val capacity=view.findViewById<TextView>(R.id.msg);
    }



}
