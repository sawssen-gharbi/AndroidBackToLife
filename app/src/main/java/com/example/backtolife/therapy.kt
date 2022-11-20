package com.example.backtolife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.backtolife.models.event

class therapy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_therapy)
        val event =intent.getParcelableExtra<event>("event")
        if(event !=null){
            val textView1=findViewById<TextView>(R.id.doctorname)
            val textView =findViewById<TextView>(R.id.therapytitle)
            val imageView =findViewById<ImageView>(R.id.imagetherapy)
            textView1.text=event.doctorName
            textView.text = event.nom
            imageView.setImageResource(event.img)


        }
    }
}