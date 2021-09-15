package com.gsm.prof_androidv2.view.upload

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.UploadItemBinding
import com.gsm.prof_androidv2.generated.callback.OnClickListener
import com.gsm.prof_androidv2.view.upload.ProjectUploadActivity.Companion.photoIndex
import com.gsm.prof_androidv2.viewmodel.ActionType
import com.gsm.prof_androidv2.viewmodel.UploadViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlin.math.log

class UploadAdapter(private val viewModel: UploadViewModel) :
    RecyclerView.Adapter<UploadAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem =
            layoutInflater.inflate(R.layout.upload_item, parent, false)

        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(photoIndex != 0){
           Glide.with(holder.itemView.context).load(viewModel.imgs.value?.get(position))
               .override(240,240)
               .into(holder.itemView.findViewById(R.id.upload_img))
        }

        holder.itemView.findViewById<ImageView>(R.id.upload_img_cancle).setOnClickListener {
            viewModel.deleteImg(position)
            viewModel.photoCount(ActionType.MINUS,1)

        }

        val layoutParams = holder.itemView.layoutParams
        layoutParams.width = 200
        holder.itemView.requestLayout()
    }

    override fun getItemCount(): Int = ProjectUploadActivity.photoIndex
}
