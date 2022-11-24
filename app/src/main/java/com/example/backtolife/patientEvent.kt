package com.example.backtolife

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.backtolife.Adapter.ListTherapy
import com.example.backtolife.models.doctor
import com.example.backtolife.models.event

lateinit var events : Array<String>
private lateinit var adapter : ListTherapy
private lateinit var recyclerView: RecyclerView
private lateinit var EventArrayList : ArrayList<event>

lateinit var  imagee : Array<Int>
lateinit var doctorName : Array<String>
lateinit var time : Array<String>
lateinit var nomm: Array<String>





class patientEvent : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (super.onViewCreated(view, savedInstanceState))
        dataInitial()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.chat_recycler)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = ListTherapy(EventArrayList)
        recyclerView.adapter = adapter
        adapter.onItemOnClick={
            val intent =Intent(this.context,therapy::class.java)
            intent.putExtra("event",it)
            startActivity(intent)
        }


    }
    private fun dataInitial(){
        EventArrayList = arrayListOf<event>()
        imagee = arrayOf(
            R.drawable._1,
            R.drawable._2,
            R.drawable._3,
            R.drawable._2,
            R.drawable._4

        )

        doctorName= arrayOf(
            "rania",
            "sawsen",
            "hamza",
            "siwar",
            "saw"
        )
        time = arrayOf(
            getString(R.string.date),
            getString(R.string.date),
            getString(R.string.date),
            getString(R.string.date),



            getString(R.string.date)
        )
        nomm = arrayOf(
            getString(R.string.title1),
            getString(R.string.title1),
            getString(R.string.title1),
            getString(R.string.title1),




            getString(R.string.title2)
        )



        for ( i in imagee.indices){

            val  events = event(nomm[i], doctorName[i], time[i], imagee[i])


            EventArrayList.add(events)
        }


        // Inflate the layout for this fragment


    }

    }
