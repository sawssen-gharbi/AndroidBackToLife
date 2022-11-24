package com.example.backtolife

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.backtolife.models.patient
import listPatient
lateinit var patients: Array<String>
private lateinit var adapter : listPatient
private lateinit var recyclerView: RecyclerView
private lateinit var patientArrayList : ArrayList<patient>

lateinit var image: Array<Int>
lateinit var titre : Array<String>
lateinit var nom : Array<String>



class ListPatient : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_patient, container, false);
    }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

            (super.onViewCreated(view, savedInstanceState))
            dataInitial()
            val layoutManager = LinearLayoutManager(context)
            recyclerView = view.findViewById(R.id.recyclePatient)
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)
            adapter = listPatient(patientArrayList)
            recyclerView.adapter = adapter

        }
        private fun dataInitial(){
            patientArrayList = arrayListOf<patient>()
            image = arrayOf(
                R.drawable._1,
                R.drawable._2,
                R.drawable._3,
                R.drawable._2,
                R.drawable._4

            )

            titre= arrayOf(
                getString(R.string.title3),
                getString(R.string.title4),
                getString(R.string.title5),
                getString(R.string.title3),
                getString(R.string.title4)
            )

            nom = arrayOf(
                getString(R.string.titre),
                getString(R.string.titre),
                getString(R.string.titre1),
                getString(R.string.titre1),




                getString(R.string.title2)
            )



            for ( i in image.indices){

                val  patients = patient(image[i], nom[i], titre[i])


                patientArrayList.add(patients)
            }



        }
    }