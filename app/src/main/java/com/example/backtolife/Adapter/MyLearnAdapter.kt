package com.example.backtolife.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.backtolife.R
import com.example.backtolife.models.Videos
import kotlinx.android.synthetic.main.activity_list_videos.view.*

class MyLearnAdapter (
    var videosList: ArrayList<Videos>,
    val itemClickListener: ItemClickListener
): RecyclerView.Adapter<MyLearnAdapter.ListViewHolder>(){


    interface ItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener{
                itemClickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_list_videos,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.itemView.imageRecyclerView.setImageResource(videosList[position].image)
        holder.itemView.textRecyclerView1.text = "${videosList[position].title}"
        holder.itemView.textRecyclerView3.text = "Duration: ${videosList[position].duration}"
    }

    override fun getItemCount(): Int {
        return videosList.size
    }
}
