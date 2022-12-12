package com.example.backtolife.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.backtolife.API.UserApi
import com.example.backtolife.ID
import com.example.backtolife.PREF_NAME
import com.example.backtolife.R
import com.example.backtolife.fragments.DEPRESSEDMOOD
import com.example.backtolife.fragments.IDREPORT
import com.example.backtolife.models.Report
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap
import kotlin.math.log

class MyReportAdapter(var reports: MutableList<Report>)  : RecyclerView.Adapter<MyReportAdapter.MyViewHolder>(){

    lateinit var mSharedPref: SharedPreferences
    var mContext: Context? = null
    lateinit var view: View
    val apiInterface = UserApi.create()
    val map: HashMap<String, String> = HashMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_report, parent, false)


        this.mContext = parent.getContext()
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        mSharedPref = mContext?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)!!



        val dateReport = reports[position].date
        val moodReport = reports[position].mood


        val psyReport = reports[position].symptoms
        holder.date.text = dateReport
        holder.mood.text = moodReport
        holder.psychotic.text = psyReport

        when (moodReport) {
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


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val date = itemView.findViewById<TextView>(R.id.dateReport)
        val mood = itemView.findViewById<TextView>(R.id.tvMoodReport)
        val psychotic = itemView.findViewById<TextView>(R.id.psychotic)
        val depressedMood = itemView.findViewById<SeekBar>(R.id.sb1)
        val elevatedMood = itemView.findViewById<SeekBar>(R.id.sb2)
        val irritabilityMood = itemView.findViewById<SeekBar>(R.id.sb3)
        val depressedText = itemView.findViewById<TextView>(R.id.depressedText)
        val elevationText = itemView.findViewById<TextView>(R.id.elevationText)
        val irritabilityText = itemView.findViewById<TextView>(R.id.irritabilityText)


        val imagee = itemView.findViewById<ImageView>(R.id.title_image_report)
        val editReportImage =
            itemView.findViewById<ImageView>(R.id.menuEditReport).setOnClickListener {
                popupMenus(it)
            }


        private fun popupMenus(v: View) {
            val position = reports[adapterPosition]
            val popupMenus = PopupMenu(mContext, v)
            popupMenus.inflate(R.menu.edit_menu)
            popupMenus.setOnMenuItemClickListener {
                if (it.itemId == R.id.editReport) {
                    val v = LayoutInflater.from(mContext).inflate(R.layout.edit_item,null)
                    val dateEdit = v.findViewById<TextView>(R.id.textViewSelectedDateEdit)
                        dateEdit.text = position.date
                    //mood
                    val moodHappyEdit = v.findViewById<TextView>(R.id.textViewHappyEdit)
                    val moodCalmEdit = v.findViewById<TextView>(R.id.textViewCalmEdit)
                    val moodManicEdit = v.findViewById<TextView>(R.id.textViewManicEdit)
                    val moodAngryEdit = v.findViewById<TextView>(R.id.textViewAngryEdit)
                    val moodSadEdit = v.findViewById<TextView>(R.id.textViewSadEdit)
                    val imageEdit = v.findViewById<ImageView>(R.id.imageViewMoodEdit)

                    when (position.mood) {
                        "Happy" -> {
                            imageEdit.setImageResource(R.drawable.happyy)
                            moodHappyEdit.setTextColor(Color.parseColor("#74C5E1"))
                        }
                        "Calm" -> imageEdit.setImageResource(R.drawable.yin_yang_symbol)
                        "Manic" -> {
                            imageEdit.setImageResource(R.drawable.celtic)
                            moodManicEdit.setTextColor(Color.parseColor("#74C5E1"))
                        }
                        "Angry" -> {
                            imageEdit.setImageResource(R.drawable.angryy)
                            moodAngryEdit.setTextColor(Color.parseColor("#74C5E1"))

                        }
                        "Sad" -> {
                            imageEdit.setImageResource(R.drawable.sadd)
                            moodSadEdit.setTextColor(Color.parseColor("#74C5E1"))

                        }
                    }
                    //d,e,i mood
                    val depressedMoodEdit = v.findViewById<SeekBar>(R.id.sliderDepressedEdit)
                    val elevatedMoodEdit = v.findViewById<SeekBar>(R.id.sliderElevatedEdit)
                    val irritabilityMoodEdit = v.findViewById<SeekBar>(R.id.sliderIrritabilityEdit)

                    depressedMoodEdit.progress = position.depressedMood
                    elevatedMoodEdit.progress =  position.elevatedMood
                    irritabilityMoodEdit.progress = position.irritabilityMood

                    //btns
                    val psychoticBtnNoEdit = v.findViewById<TextView>(R.id.btnPsychoticSymptomsNoEdit)
                    val psychoticBtnYesEdit = v.findViewById<TextView>(R.id.PsychoticSymptomsYesEdit)

                    when(position.symptoms){
                        "Yes" ->{
                            psychoticBtnYesEdit.setTextColor(Color.parseColor("#FF000000"))
                        }
                        "No" -> {
                            psychoticBtnNoEdit.setTextColor(Color.parseColor("#FF000000"))


                        }
                    }
                     var id = position._id
                    AlertDialog.Builder(mContext)
                        .setView(v)
                        .setPositiveButton("Ok"){
                                dialog,_->
                            map["depressedMood"] = depressedMoodEdit.progress.toString()
                            map["elevatedMood"] = elevatedMoodEdit.progress.toString()
                            map["irritabilityMood"] = irritabilityMoodEdit.progress.toString()

                            apiInterface.editReport(id,map).enqueue(object: Callback<Report> {
                                override fun onResponse(call: Call<Report>, response: Response<Report>)
                                {
                                    if(response.isSuccessful){
                                        Log.e("You edited a report",":)")
                                    }

                                }

                                override fun onFailure(call: Call<Report>, t: Throwable)
                                {
                                    println("okay")
                                }
                            })

                            notifyDataSetChanged()
                            Toast.makeText(mContext,"User Information is Edited",Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        }
                        .setNegativeButton("Cancel"){
                                dialog,_->
                            dialog.dismiss()

                        }
                        .create()
                        .show()
                    true
                }
                  else true

                }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
            }


    }

        fun deleteItem(index: Int) {

            com.example.backtolife.fragments.apiInterface.deleteReport(reports.removeAt(index)._id)
                .enqueue(object: Callback<Report> {
                    override fun onResponse(call: Call<Report>, response: Response<Report>)
                    {
                        if (response.isSuccessful){
                            Log.i("Report Deleted", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<Report>, t: Throwable)
                    {
                        println("okay")
                    }
                })
            notifyDataSetChanged()
        }




}











