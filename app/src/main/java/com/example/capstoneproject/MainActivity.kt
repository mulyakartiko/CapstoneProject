package com.example.capstoneproject

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.adapter.DataAdapter
import com.example.capstoneproject.ml.Model
import com.example.capstoneproject.model.DataItem
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer


class MainActivity : AppCompatActivity() {

    lateinit var dataBase: DatabaseReference
    private lateinit var dtList: ArrayList<DataItem>
    private lateinit var dtAdapter:DataAdapter
    lateinit var result: TextView
    lateinit var diagnosa: Button
    lateinit var select: Button
    lateinit var foto: Button
    lateinit var bitmap: Bitmap
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dtList = ArrayList()
        dtAdapter = DataAdapter(this, dtList)
        rv_penyakit.layoutManager = LinearLayoutManager(this)
        rv_penyakit.setHasFixedSize(true)
        getItemData()

        imageView = findViewById(R.id.gambarpenyakit)
        diagnosa = findViewById(R.id.btndiagnosa)
        select = findViewById(R.id.btnselect)
        result = findViewById(R.id.tv_result)
        foto = findViewById(R.id.btncamera)

        val label = application.assets.open("labels.txt").bufferedReader()
                .use { it.readText() }.split("\n")

        select.setOnClickListener(View.OnClickListener {
            var intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            startActivityForResult(intent, 100)
        })

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1888)

        foto.setOnClickListener {
            val cameraIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, 1888)
        }

        diagnosa.setOnClickListener(View.OnClickListener {
            val resize: Bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true)
            val model = Model.newInstance(this)
            val buffer = TensorImage.fromBitmap(resize)
            val byteBuffer = buffer.buffer

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 100, 100, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            var max = getMax(outputFeature0.floatArray)

            result.setText(label[max])

            model.close()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1888) {
            var photo = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(photo)
            bitmap = photo
        } else {
        imageView.setImageURI(data?.data)
        var uri: Uri ?= data?.data
        bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
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

    fun getMax(array: FloatArray) : Int{
        var index = 0
        var min = 0.0f

        for (n in 0..4){
            if (min < array[n]){
                index = n
                min = array[n]
            }
        }
        return index
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

