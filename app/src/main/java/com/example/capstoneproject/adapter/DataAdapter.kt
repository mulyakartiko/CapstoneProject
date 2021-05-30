package com.example.capstoneproject.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.DetailActivity
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.LayoutItemBinding
import com.example.capstoneproject.model.DataItem

class DataAdapter(var ctx: Context, var dataList:ArrayList<DataItem>): RecyclerView.Adapter<DataAdapter.DataViewHolder>() {
    inner class DataViewHolder(var v:LayoutItemBinding): RecyclerView.ViewHolder(v.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<LayoutItemBinding>(inflater, R.layout.layout_item, parent, false)
        return DataViewHolder(v)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val newDataList = dataList[position]
        holder.v.iniData = dataList[position]
        holder.v.root.setOnClickListener{
            val nama = newDataList.nama
            val img = newDataList.img
            val gejala = newDataList.gejala
            val obat = newDataList.obat
            val penyebab = newDataList.penyebab
            val cegah = newDataList.cegah

            val dintent = Intent(ctx, DetailActivity::class.java)
            dintent.putExtra("img", img)
            dintent.putExtra("nama", nama)
            dintent.putExtra("obat", obat)
            dintent.putExtra("gejala", gejala)
            dintent.putExtra("penyebab", penyebab)
            dintent.putExtra("cegah", cegah)
            ctx.startActivity(dintent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}