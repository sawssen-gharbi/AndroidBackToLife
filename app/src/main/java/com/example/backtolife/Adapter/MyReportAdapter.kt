package com.example.backtolife.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.backtolife.R
import com.example.backtolife.models.Reports
import com.google.android.material.imageview.ShapeableImageView

class MyReportAdapter(private val reportList : ArrayList<Reports>)  : RecyclerView.Adapter<MyReportAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_report,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = reportList[position]
        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.mood.text =currentItem.mood
        holder.date.text = currentItem.date
        holder.depressed.text = currentItem.depressedPourcentage
        holder.elevated.text = currentItem.elevatedPourcentage
        holder.irritability.text = currentItem.irritabilityPourcentage
        holder.psychotic.text = currentItem.psychoticSymptoms

    }

    override fun getItemCount(): Int {
        return reportList.size


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        val titleImage : ShapeableImageView = itemView.findViewById(R.id.title_image_report)
        val date = itemView.findViewById<TextView>(R.id.dateReport)
        val mood  = itemView.findViewById<TextView>(R.id.tvMoodReport)
        val depressed = itemView.findViewById<TextView>(R.id.reportDepressed)
        val elevated = itemView.findViewById<TextView>(R.id.reportElevated)
        val irritability = itemView.findViewById<TextView>(R.id.reportIrritability)
        val psychotic = itemView.findViewById<TextView>(R.id.reportPsychotic)
    }

}