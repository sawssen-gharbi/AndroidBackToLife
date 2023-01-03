package com.example.backtolife

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.backtolife.fragments.*

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Suppress("DEPRECATION")
class MainDoctor : AppCompatActivity() {
    private lateinit var mSharedPref: SharedPreferences

    lateinit var bottomNav: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navViewd: NavigationView
    private lateinit var topAppBar: Toolbar
    private lateinit var butLog: Button

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_doctor)
        loadFragment(ajoutEvent())
        bottomNav = findViewById(R.id.bottomNav)
        drawerLayout = findViewById(R.id.drawer_layoutd)
        navViewd = findViewById(R.id.hi)
        topAppBar = findViewById(R.id.topAppBard)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        butLog = findViewById(R.id.log)
        butLog.setOnClickListener {
            showAlertDialog()
        }

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navViewd.setNavigationItemSelectedListener { menuItem ->


            when (menuItem.itemId) {
                R.id.navArticle -> refresh(ArticlesFragment(), menuItem.title.toString())
                R.id.nav_share -> {
                    var shareIntent : Intent =  Intent()
                    shareIntent.setAction(Intent.ACTION_SEND)
                    shareIntent.putExtra(Intent.EXTRA_TEXT,"https://github.com/ranianadine28/BackToLifeAndroid")
                    shareIntent.setType("text/plain")
                    startActivity(Intent.createChooser(shareIntent,"share via"))
                }
            }
            drawerLayout.close()
            true


        }






        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeDoctor -> loadFragment(ajoutEvent())

                R.id.itemEvent -> loadFragment(eventDoctor2())
                R.id.listP -> loadFragment(ListPatient())
                R.id.itemP -> loadFragment((DoctorProfileFragment()))

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
        fragmentTransaction.replace(R.id.frame_doctor, fragment)
        fragmentTransaction.commit()
        setTitle(title)
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_doctor, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showAlertDialog(){
        MaterialAlertDialogBuilder(this)
            .setTitle("Logout")
            .setMessage("are you sure you want to logout?")
            .setPositiveButton("Ok") {dialog, which ->
                mSharedPref.edit().clear().apply()

                Activity().finish()
                val mainIntent = Intent(this, Login ::class.java)
                startActivity(mainIntent)
            }
            .setNegativeButton("Cancel") {dialog, which ->

            }
            .show()
    }

}