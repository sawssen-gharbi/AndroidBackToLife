import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.backtolife.R
import com.example.backtolife.models.patient
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class ListPatientAdapter(val listpatient: MutableList<patient>):RecyclerView.Adapter<ListPatientAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder

            =  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_patient,parent,false))

    fun addAll(list: MutableList<patient>){
        listpatient.addAll(list)
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: patient=listpatient[position]
        Glide.with(holder.itemView).load(data.image).into(holder.img_user)
        holder.fullName.text=data.fullName
        holder.phone.text=data.phone
    }





    override fun getItemCount(): Int = listpatient.size;

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val fullName=view.findViewById<TextView>(R.id.name_user);
        val phone=view.findViewById<TextView>(R.id.capacity);
        val img_user = view.findViewById<ImageView>(R.id.img_user);






    }



}

