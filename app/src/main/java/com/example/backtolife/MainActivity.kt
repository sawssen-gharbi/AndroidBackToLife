package com.example.backtolife

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.backtolife.fragments.HomeFragment
import com.example.backtolife.fragments.ReportFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {


    private lateinit var toogle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.nav_menu_drawer)

        toogle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
                it.isChecked = true
            when (it.itemId) {

                R.id.navHome -> refresh(HomeFragment(),it.title.toString())
                R.id.navReport -> refresh(ReportFragment(),it.title.toString())
            }
            true

        }


    }

    private fun refresh(fragment: Fragment, title: String) {

        val fragementManager = supportFragmentManager
                val fragmentTransaction = fragementManager.beginTransaction()
     fragmentTransaction.replace(R.id.framedrawer,fragment)
        fragmentTransaction.commit()
        setTitle(title)
            }


}
