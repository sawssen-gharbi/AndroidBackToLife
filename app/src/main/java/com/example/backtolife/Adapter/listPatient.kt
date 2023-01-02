import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.backtolife.R
import com.example.backtolife.models.event
import com.example.backtolife.models.patient

import de.hdodenhof.circleimageview.CircleImageView

class listPatient(val listpatient:ArrayList<patient>):RecyclerView.Adapter<listPatient.ViewHolder>() {
    var onItemOnClick: ((patient)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_patient,parent,false);
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val patient: patient =listpatient[position]

        holder.itemView.setOnClickListener{
            onItemOnClick?.invoke(patient)
        }

    }

    override fun getItemCount(): Int {
        return listpatient.size;
    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val nom=view.findViewById<TextView>(R.id.name_user);
        val image=view.findViewById<CircleImageView>(R.id.img_user);

    }

}
