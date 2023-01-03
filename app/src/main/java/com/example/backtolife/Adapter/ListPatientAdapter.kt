import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.backtolife.*
import com.example.backtolife.API.UserApi
import com.example.backtolife.Adapter.ClassicAdapter
import com.example.backtolife.fragments.IMAGEU
import com.example.backtolife.models.*
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.time.format.DateTimeFormatter


class ListPatientAdapter(val listInv: MutableList<ReservationItem>) :
    ClassicAdapter<ListPatientAdapter.ViewHolder, ReservationItem>(listInv) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patient, parent, false));


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mSharedPref =
            holder.itemView.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)!!
        val data: ReservationItem = listInv[position]
        //  val g : User = data.patient
        //  Log.e("g",g.toString())

        var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
        holder.name_user.text = data.patient.fullName

        holder.datee.text = DateFormat.getDateInstance().format(data.date)
      //  Glide.with(holder.itemView).load(data.patient.photo).into(holder.img_user)

        Picasso.get()
            .load("http://192.168.100.153:7001/image/${data.patient.photo}")
            .networkPolicy(NetworkPolicy.NO_STORE, NetworkPolicy.NO_CACHE)
            .placeholder(R.drawable.angryy)
            .into(holder.img_user)


        //actions click

        holder.accept.setOnClickListener {
            acceptInvitation(data._id, position)
            mSharedPref.edit().apply() {
                putString(STATUS, data.status)
            }.apply()
        }

        holder.refuse.setOnClickListener {
            refuseInvitation(data._id, position)
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img_user = view.findViewById<ImageFilterView>(R.id.img_user_inv);
        val name_user = view.findViewById<TextView>(R.id.name_user_inv);
        val datee = view.findViewById<TextView>(R.id.date_inv);
        val accept = view.findViewById<Button>(R.id.accept_inv);
        val refuse = view.findViewById<Button>(R.id.refuse_inv);
    }


    private fun acceptInvitation(_id: String, position: Int) {

        val map = HashMap<String, String>()
        map.put("id", _id);
        val apiInterface = UserApi.create()
        apiInterface.acceptInvitation(map).enqueue(object : Callback<ServerResponse> {
            override fun onResponse(
                call: Call<ServerResponse>,
                response: Response<ServerResponse>,
            ) {
                if (response.isSuccessful) {

                    notifyItemRemoved(position)
                }
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("onFailure_Acceptinvi", t.toString())
            }
        })
    }


    private fun refuseInvitation(_id: String, position: Int) {
        val map = HashMap<String, String>()
        map.put("id", _id);
        val apiInterface = UserApi.create()
        apiInterface.refuseInvitation(map).enqueue(object : Callback<ServerResponse> {
            override fun onResponse(
                call: Call<ServerResponse>,
                response: Response<ServerResponse>,
            ) {
                if (response.isSuccessful) {
                    listInv.removeAt(position)
                    notifyItemRemoved(position)
                }
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
                Log.e("onFailure_Refuseinvi", t.toString())
            }
        })
    }
}