package com.example.capstoneproject.ui

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.capstoneproject.R

fun getProgessDrawable(ctx: Context): CircularProgressDrawable {
    return CircularProgressDrawable(ctx).apply {
        strokeWidth = 5f
        centerRadius = 40f
        start()
    }

}

/**set Images*/
fun ImageView.loadImg(uri:String?, progressDawable: CircularProgressDrawable){

    val option = RequestOptions().placeholder(progressDawable)
        .error(R.mipmap.ic_launcher)

    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(uri)
        .into(this)

}

@BindingAdapter("android:imageUrl")
fun loadImg(view: ImageView, url:String){
    view.loadImg(url, getProgessDrawable(view.context))
}