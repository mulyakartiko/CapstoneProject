package com.example.capstoneproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.ui.getProgessDrawable
import com.example.capstoneproject.ui.loadImg
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val dataIntent = intent
        val pnama = dataIntent.getStringExtra("nama")
        val pimg = dataIntent.getStringExtra("img")
        val pgejala = dataIntent.getStringExtra("gejala")
        val pobat = dataIntent.getStringExtra("obat")
        val ppenyebab = dataIntent.getStringExtra("penyebab")
        val pcegah = dataIntent.getStringExtra("cegah")

        nama.text = pnama
        gejala.text = pgejala
        obat.text = pobat
        penyebab.text = ppenyebab
        cegah.text = pcegah
        img.loadImg(pimg, getProgessDrawable(this))

    }
}