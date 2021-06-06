package com.example.capstoneproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*


class MainActivity : AppCompatActivity() {

    private lateinit var iv_lp : ImageView
    private lateinit var iv_diag : ImageView
    private lateinit var iv_setting : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iv_lp = findViewById(R.id.iv_list_penyakit)
        iv_diag = findViewById(R.id.iv_diagnosa)
        iv_setting = findViewById(R.id.iv_pengaturan)

        iv_lp.setOnClickListener {
            val i = Intent(this, ListPenyakitActivity::class.java)
            startActivity(i)
        }

        iv_diag.setOnClickListener {
            val i = Intent(this, DiagnosaActivity::class.java)
            startActivity(i)
        }

        iv_setting.setOnClickListener {
            val i = Intent(this, PengaturanActivtiy::class.java)
            startActivity(i)
        }
    }
}