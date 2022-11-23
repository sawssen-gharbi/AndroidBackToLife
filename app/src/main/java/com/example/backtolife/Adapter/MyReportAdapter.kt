package com.example.backtolife.Adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
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
        holder.date.text = dateReport


        }


    override fun getItemCount(): Int {
        return reports.size
    }



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        val date = itemView.findViewById<TextView>(R.id.dateReport)

    }






}