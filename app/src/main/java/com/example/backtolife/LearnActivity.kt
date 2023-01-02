package com.example.backtolife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.backtolife.Adapter.MyLearnAdapter
import com.example.backtolife.models.LearnViewModel


class LearnActivity : AppCompatActivity() , MyLearnAdapter.ItemClickListener {
    private lateinit var viewModel: LearnViewModel
    private lateinit var topAppBar : Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        viewModel = ViewModelProvider(this).get(LearnViewModel::class.java)

        var rv = findViewById<RecyclerView>(R.id.recyclerView1)

        val adapter = MyLearnAdapter(viewModel.getData(), this)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        topAppBar = findViewById(R.id.topAppBarLearn)
        setSupportActionBar(topAppBar)

        topAppBar.setNavigationOnClickListener {
            onBackPressed()

        }

    }

    override fun onItemClick(position: Int) {
           val intent = Intent(this, VideoActivity::class.java)
            intent.putExtra("videos", viewModel.getData()[position])
            startActivity(intent)

    }
}