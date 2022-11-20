package com.example.backtolife

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.backtolife.Adapter.ListEvent
import com.example.backtolife.models.doctor
lateinit var doctors : Array<String>
private lateinit var adapter : ListEvent
private lateinit var recyclerView: RecyclerView
private lateinit var doctorArrayList : ArrayList<doctor>

lateinit var img : Array<Int>
lateinit var capacity : Array<String>
lateinit var date : Array<String>
lateinit var title: Array<String>

class EventDoctor : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_doctor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (super.onViewCreated(view, savedInstanceState))
        dataInitial()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycleDoctor)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = ListEvent(doctorArrayList)
        recyclerView.adapter = adapter

    }
    private fun dataInitial(){
       doctorArrayList = arrayListOf<doctor>()
        img = arrayOf(
            R.drawable._1,
            R.drawable._2,
            R.drawable._3,
            R.drawable._2,
            R.drawable._4

        )

        capacity= arrayOf(
            getString(R.string.capacity),
            getString(R.string.capacity),
            getString(R.string.capacity),
            getString(R.string.capacity),
            getString(R.string.capacity)
        )
        date = arrayOf(
            getString(R.string.date),
            getString(R.string.date),
            getString(R.string.date),
            getString(R.string.date),



            getString(R.string.date)
        )
        title = arrayOf(
            getString(R.string.title1),
            getString(R.string.title1),
            getString(R.string.title1),
            getString(R.string.title1),




            getString(R.string.title2)
        )



        for ( i in img.indices){

            val  doctors = doctor(img[i], capacity[i],date[i], title[i])


            doctorArrayList.add(doctors)
        }



    }
}