package com.example.capstoneproject

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {
    private lateinit var btnback: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        btnback = findViewById(R.id.btnback)
        btnback.setOnClickListener{
            val back = Intent(this, MainActivity::class.java)
            startActivity(back)
        }
    }
}