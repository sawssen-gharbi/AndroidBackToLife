package com.example.backtolife.Adapter


import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.backtolife.R
import com.example.backtolife.models.Therapy


class ListEventAdap(var therapies: MutableList<Therapy>) :RecyclerView.Adapter<ListEventAdap.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder

       =  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_group,parent,false))


   /* override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(therapies[position])
    Glide.with(holder)
    .load(data.u.image)
    .into(holder.img) }*/
    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: Therapy =therapies[position];
        //load image user
       try {
           var url = data.image /* URL of Image */
           if (url.startsWith("http://")) url = url.replace("http://", "https://")
           val requestOptions = RequestOptions()
           requestOptions.placeholder(R.drawable._1)
           requestOptions.error(R.drawable._1)
           Glide
               .with(holder.itemView)
               .setDefaultRequestOptions(requestOptions)
               .load(url)
               .into(holder.imgUser)
       } catch (e: Exception) {
           e.printStackTrace()
       }
      /* Glide.with(holder.itemView)
           .load(data.image)
           .placeholder(R.drawable._1)
           .into(holder.imgUser);*/

      /* GlideApp.with(holder.itemView)
            .load(data.image)
            .into(holder.imgUser as ImageView)*/

        //Convert date format
        /*val sdf=SimpleDateFormat("dd/MM/yyyy")
        holder.date.text=sdf.format(data.date)*/
        //endConvert date

        //if text empty


        if (data.image.isNullOrEmpty() || data.image.equals("empty")){
            holder.imgUser.visibility=View.GONE;
        }else{
            holder.imgUser.visibility=View.VISIBLE;
            //load image post
            try {
                var url = data.image /* URL of Image */
                if (url.startsWith("http://")) url = url.replace("http://", "https://")
                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.drawable._1)
                requestOptions.error(R.drawable._1)
                Glide
                    .with(holder.itemView)
                    .setDefaultRequestOptions(requestOptions)
                    .load(url)
                    .into(holder.imgUser)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getItemCount(): Int = therapies.size;

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        fun bind(therapy: Therapy) {

            titre?.text = therapy.titre

            date?.text = therapy.date

            capacity?.text = "${therapy.capacity}"

           /* Picasso.get()
                .load(therapy.image)
                .resize(300, 300)
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.angryy)
                .into(imgUser!!)*/
        }

        private var titre: TextView?= null
        private var date: TextView? = null
        private var capacity: TextView? = null

        init {
             titre=view.findViewById(R.id.name_user)
            date=view.findViewById(R.id.time)

             capacity =view.findViewById(R.id.capacity)
        }

        val imgUser = view.findViewById<ImageView>(R.id.img_user)

    }



}
