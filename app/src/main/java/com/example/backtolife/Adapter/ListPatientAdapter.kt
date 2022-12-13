import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.backtolife.API.UserApi
import com.example.backtolife.R
import com.example.backtolife.models.ServerResponse
import com.example.backtolife.models.Therapy
import com.example.backtolife.models.patient
import com.example.backtolife.models.reservation
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.format.DateTimeFormatter

class ListPatientAdapter(val listInv:ArrayList<reservation>):RecyclerView.Adapter<ListPatientAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_patient,parent,false);
        return ListPatientAdapter.ViewHolder(view);
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data:reservation=listInv[position]

        holder.date.text= data.date.toString()


        //actions click

        holder.accept.setOnClickListener {
            acceptInvitation(data._id,position)
        }

        holder.refuse.setOnClickListener {
            refuseInvitation(data._id,position)
        }
    }




    override fun getItemCount(): Int {
        return listInv.size
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        //val img_user=view.findViewById<ImageFilterView>(R.id.img_user_inv);
        val name_user=view.findViewById<TextView>(R.id.name_user_inv);
        val date=view.findViewById<TextView>(R.id.date_inv);
        val accept=view.findViewById<Button>(R.id.accept_inv);
        val refuse=view.findViewById<Button>(R.id.refuse_inv);
    }


    private fun acceptInvitation(_id: String, position: Int) {

        val map=HashMap<String,String>()
        map.put("id",_id);
        val apiInterface = UserApi.create()
 apiInterface.acceptInvitation(map).enqueue(object : Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                if (response.isSuccessful){
                    listInv.removeAt(position)
                    notifyItemRemoved(position)
                }
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("onFailure_Acceptinvi",t.toString())
            }
        })
    }


    private fun refuseInvitation(_id: String, position: Int) {
        val map=HashMap<String,String>()
        map.put("id",_id);
        val apiInterface = UserApi.create()
        apiInterface.refuseInvitation(map).enqueue(object : Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                if (response.isSuccessful){
                    listInv.removeAt(position)
                    notifyItemRemoved(position)
                }
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("onFailure_Refuseinvi",t.toString())
            }
        })
    }
}