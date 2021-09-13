package com.gsm.prof_androidv2.view.upload

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gsm.prof_androidv2.databinding.UploadItemBinding

class UploadAdapter(private val imgs: ArrayList<Uri>,val context :Context): RecyclerView.Adapter<UploadAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: UploadItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(uri: Uri) {
            Glide.with(context).load(uri)
                .into(binding.uploadImg)

            binding.uploadImgCancle.setOnClickListener{
                imgs.remove(uri)
                notifyItemRemoved(imgs.indexOf(uri))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UploadItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder =ViewHolder(binding)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imgs.get(position))

        val layoutParams = holder.itemView.layoutParams
        layoutParams.width = 300
        holder.itemView.requestLayout()
    }

    override fun getItemCount(): Int = imgs.size
}