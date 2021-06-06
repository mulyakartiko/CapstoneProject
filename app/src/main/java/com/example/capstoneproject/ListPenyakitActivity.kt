package com.example.capstoneproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.adapter.DataAdapter
import com.example.capstoneproject.model.DataItem
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_list_penyakit.*

class ListPenyakitActivity : AppCompatActivity() {
    lateinit var dataBase: DatabaseReference
    private lateinit var dtList: ArrayList<DataItem>
    private lateinit var dtAdapter: DataAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_penyakit)

        dtList = ArrayList()
        dtAdapter = DataAdapter(this, dtList)
        rv_penyakit.layoutManager = LinearLayoutManager(this)
        rv_penyakit.setHasFixedSize(true)
        getItemData()
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
                Toast.makeText(this@ListPenyakitActivity,
                    error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}