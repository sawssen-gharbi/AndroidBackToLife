package com.example.backtolife

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.backtolife.fragments.HomeFragment
import com.example.backtolife.fragments.ListesFragment
import com.example.backtolife.fragments.ProfileFragment
import com.example.backtolife.fragments.ReportFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivityPatient : AppCompatActivity() {


    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_patient)
        loadFragment(HomeFragment())
        bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView_patient)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.itemHome -> loadFragment(HomeFragment())

                R.id.itemReport -> loadFragment(ReportFragment())

                R.id.itemList -> loadFragment(patientEvent())

                R.id.itemProfile -> loadFragment(ProfileFragment())

            }
            true
        }
    }


    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout_patient,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}