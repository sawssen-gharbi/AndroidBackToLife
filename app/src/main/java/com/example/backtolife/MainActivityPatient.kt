package com.example.backtolife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.backtolife.fragments.HomeFragment
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
    private lateinit var mSharedPref: SharedPreferences

    private lateinit var header : View
    private lateinit var  fullNameHeader : TextView
    private lateinit var  emailHeader : TextView
    private lateinit var  imageHeader : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_patient)
        bottomNav = findViewById(R.id.bottomNavigationView_patient)




        refresh(HomeFragment(),title.toString())



        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        topAppBar = findViewById(R.id.topAppBar)

        supportActionBar?.setDisplayShowTitleEnabled(true);



        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()



        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()

        }

        navView.setNavigationItemSelectedListener { menuItem ->



            when (menuItem.itemId) {

                R.id.navTM -> redirectActivity(this,LearnActivity::class.java)
                R.id.navBreath ->  redirectActivity(this,BreathActivity::class.java)
                //R.id.navMessage ->
            }
            drawerLayout.close()
            true

        }

        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        // header
        header = navView.getHeaderView(0)
        fullNameHeader = header.findViewById(R.id.fullNameHeader)
        emailHeader = header.findViewById(R.id.emailHeader)
        imageHeader = header.findViewById<ImageView>(R.id.imageHeader)

        val textName = mSharedPref.getString(FULLNAME, "").toString()
        val textEmail = mSharedPref.getString(EMAIL, "").toString()

        fullNameHeader.text = textName
        emailHeader.text = textEmail



        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemHome ->{ refresh(HomeFragment(),menuItem.title.toString())
                }

                R.id.itemReport -> refresh (ReportFragment(),menuItem.title.toString())

                R.id.itemTherapy -> refresh(listTherapy(),menuItem.title.toString())

                R.id.itemProfile -> refresh(ProfileFragment(),menuItem.title.toString())

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


    fun redirectActivity(activity: Activity, Class: Class<*>?) {
        val intent = Intent(activity, Class)
        activity.startActivity(intent)
    }

}