package com.example.capstoneproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.adapter.DataAdapter
import com.example.capstoneproject.model.DataItem
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var dataBase: DatabaseReference
    private lateinit var dtList: ArrayList<DataItem>
    private lateinit var dtAdapter:DataAdapter
    private val cameraRequest = 1888

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dtList = ArrayList()
        dtAdapter = DataAdapter(this, dtList)
        rv_penyakit.layoutManager = LinearLayoutManager(this)
        rv_penyakit.setHasFixedSize(true)
        getItemData()

        val photoButton: ImageButton = findViewById(R.id.btnfoto)
        photoButton.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequest)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return true
        }
    }

    private fun getItemData() {

        dataBase = FirebaseDatabase.getInstance().getReference("Penyakit")
        dataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (dataSnapshot in snapshot.children){
                        val pitem = dataSnapshot.getValue(DataItem::class.java)
                        dtList.add(pitem!!)
                    }
                    rv_penyakit.adapter = dtAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity,
                        error.message, Toast.LENGTH_SHORT).show()
            }

        })


    }

}