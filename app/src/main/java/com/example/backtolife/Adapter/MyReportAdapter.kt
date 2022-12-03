package com.example.backtolife.Adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.backtolife.R
import com.example.backtolife.models.Report
import com.example.backtolife.models.Reports
import com.google.android.material.imageview.ShapeableImageView

class MyReportAdapter(var reports: MutableList<Report>)  : RecyclerView.Adapter<MyReportAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_report, parent,false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dateReport = reports[position].date
        val moodReport = reports[position].mood


        val psyReport = reports[position].symptoms
        holder.date.text = dateReport
        holder.mood.text = moodReport
        holder.psychotic.text = psyReport

        when(moodReport){
            "Happy" -> holder.imagee.setImageResource(R.drawable.happyy)
            "Calm" -> holder.imagee.setImageResource(R.drawable.yin_yang_symbol)
            "Manic" -> holder.imagee.setImageResource(R.drawable.celtic)
            "Angry" -> holder.imagee.setImageResource(R.drawable.angryy)
            "Sad" -> holder.imagee.setImageResource(R.drawable.sadd)
        }


        val seekDepressed = reports[position].depressedMood
        holder.depressedMood.progress = seekDepressed
        holder.depressedMood.isEnabled = false
        holder.depressedText.text = "$seekDepressed"



        val seekElevation = reports[position].elevatedMood
        holder.elevatedMood.progress = seekElevation
        holder.elevatedMood.isEnabled = false
        holder.elevationText.text = "$seekElevation"

        val seekIrritbility = reports[position].irritabilityMood
        holder.irritabilityMood.progress = seekIrritbility
        holder.irritabilityMood.isEnabled = false
        holder.irritabilityText.text = "$seekIrritbility"



        }


    override fun getItemCount(): Int {
        return reports.size
    }



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        val date = itemView.findViewById<TextView>(R.id.dateReport)
        val mood = itemView.findViewById<TextView>(R.id.tvMoodReport)
        val psychotic = itemView.findViewById<TextView>(R.id.psychotic)
        val depressedMood = itemView.findViewById<SeekBar>(R.id.sb1)
        val elevatedMood = itemView.findViewById<SeekBar>(R.id.sb2)
        val irritabilityMood = itemView.findViewById<SeekBar>(R.id.sb3)
        val depressedText =  itemView.findViewById<TextView>(R.id.depressedText)
        val elevationText =  itemView.findViewById<TextView>(R.id.elevationText)
        val irritabilityText =  itemView.findViewById<TextView>(R.id.irritabilityText)
        val imagee =  itemView.findViewById<ImageView>(R.id.title_image_report)
    }

    fun deleteItem(index: Int) {
        reports.removeAt(index)
        notifyDataSetChanged()
    }





}