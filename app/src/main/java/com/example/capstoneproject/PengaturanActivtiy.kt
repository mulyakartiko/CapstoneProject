package com.example.capstoneproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PengaturanActivtiy : AppCompatActivity() {
    private lateinit var tv_kontak : TextView
    private lateinit var tv_about : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaturan_activtiy)

        tv_about = findViewById(R.id.tv_tentang)
        tv_kontak = findViewById(R.id.tv_kontak)

        tv_kontak.setOnClickListener {
            val i = Intent(this, KontakActivity::class.java)
            startActivity(i)
        }

        tv_about.setOnClickListener {
            val i = Intent(this, AboutActivity::class.java)
            startActivity(i)
        }
    }
}