package com.example.backtolife.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.backtolife.R
import com.example.backtolife.models.event

import de.hdodenhof.circleimageview.CircleImageView

class ListTherapy(val listgroup:ArrayList<event>):RecyclerView.Adapter<ListTherapy.ViewHolder>() {
    var onItemOnClick: ((event)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_group,parent,false);
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ListTherapy.ViewHolder, position: Int) {
        val event: event =listgroup[position]
        holder.nomm.text=event.nomm
        holder.msg.text=event.doctorName;
        holder.time.text=event.time;
        holder.imagee.setImageResource(event.imagee);
        holder.itemView.setOnClickListener{
            onItemOnClick?.invoke(event)
        }

    }

    override fun getItemCount(): Int {
        return listgroup.size;
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val nomm=itemView.findViewById<TextView>(R.id.name_user);
        val imagee=itemView.findViewById<CircleImageView>(R.id.img_user);
        val time=itemView.findViewById<TextView>(R.id.time);
        val msg=itemView.findViewById<TextView>(R.id.capacity);
    }




}