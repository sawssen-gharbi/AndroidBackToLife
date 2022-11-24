package com.example.backtolife

import android.app.Activity
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.backtolife.fragments.HomeFragment

import com.google.android.material.bottomnavigation.BottomNavigationView

lateinit var bottomNav : BottomNavigationView

@Suppress("DEPRECATION")
class MainDoctor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_doctor)
        loadFragment(ajoutEvent())
        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeDoctor -> loadFragment(ajoutEvent())

                R.id.itemEvent -> loadFragment(EventDoctor())
                    R.id.listP->loadFragment(ListPatient())
                R.id.itemP->loadFragment((doctorprofile()))



            }
            true
        }
    }


    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_doctor,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}