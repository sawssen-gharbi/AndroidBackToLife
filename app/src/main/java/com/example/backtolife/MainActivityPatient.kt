package com.example.backtolife

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.backtolife.fragments.HomeFragment
import com.example.backtolife.fragments.ListesFragment
import com.example.backtolife.fragments.ProfileFragment
import com.example.backtolife.fragments.ReportFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivityPatient : AppCompatActivity() {


    lateinit var bottomNav : BottomNavigationView

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var topAppBar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_patient)
        loadFragment(HomeFragment())
        bottomNav = findViewById(R.id.bottomNavigationView_patient)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        topAppBar = findViewById(R.id.topAppBar)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()



        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }

        navView.setNavigationItemSelectedListener { menuItem ->



            when (menuItem.itemId) {

                R.id.navHome -> refresh(listTherapy(),menuItem.title.toString())
                R.id.groupm -> refresh(ajoutEvent(),menuItem.title.toString())
            }
            drawerLayout.close()
            true

        }

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.itemHome -> loadFragment(HomeFragment())

                R.id.itemReport -> loadFragment(ReportFragment())

                R.id.itemTherapy -> loadFragment(listTherapy())

                R.id.itemProfile -> loadFragment(ProfileFragment())

            }
            true
        }
    }
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    private fun refresh(fragment: Fragment, title: String) {

        val fragementManager = supportFragmentManager
        val fragmentTransaction = fragementManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout_patient,fragment)
        fragmentTransaction.commit()
        setTitle(title)
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout_patient,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}