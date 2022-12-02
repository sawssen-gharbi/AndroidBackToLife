import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =



        holder.bind(listpatient[position])


    override fun getItemCount(): Int = listpatient.size;

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        fun bind(patient: patient) {

            fullName?.text = patient.fullName

            phone?.text = patient.phone



            Picasso.get()
                .load(patient.image)
                .resize(300, 300)
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.angryy)
                .into(img_user!!)
        }

        private var phone: TextView?= null
        private var fullName: TextView? = null
        private var img_user: ImageView? = null


        init {
            phone=view.findViewById(R.id.capacity)
            fullName=view.findViewById(R.id.name_user)
            img_user = view.findViewById(R.id.img_user)

        }



    }



}

