package com.example.backtolife.Adapter


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.backtolife.EditTherapy
import com.example.backtolife.Login
import com.example.backtolife.R
import com.example.backtolife.models.Therapy
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter


class ListEventAdap(var therapies: MutableList<Therapy>) :RecyclerView.Adapter<ListEventAdap.ViewHolder>(),Filterable {
 var therapisFilteredList: List<Therapy> = ArrayList()
    init {
        therapisFilteredList=therapies
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_doctor,parent,false)
        return ListEventAdap.ViewHolder(view);
    }



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val data: Therapy = therapisFilteredList[position];


       //convert date type
        var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")

       Glide.with(holder.itemView).load(data.image).into(holder.imgUser)
       holder.name.text=data.titre
       holder.capacity.text=data.capacity.toString()
        holder.location.text=data.address.toString()
       holder.time.text=data.date.format(formatter)
       Log.e("image path :",data.image)
       holder.butU.setOnClickListener {
           val i=Intent(it.context,EditTherapy::class.java)
           it.context.startActivity(i)
       }



   }
    override fun getItemCount(): Int = therapisFilteredList.size;

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val name=view.findViewById<TextView>(R.id.name_user);
        val capacity=view.findViewById<TextView>(R.id.capacity);
        val imgUser = view.findViewById<ImageView>(R.id.img_post);
        val location=view.findViewById<TextView>(R.id.location)
        val time=view.findViewById<TextView>(R.id.time);
        val butU=view.findViewById<Button>(R.id.editT)


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
