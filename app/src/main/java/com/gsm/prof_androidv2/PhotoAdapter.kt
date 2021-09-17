package com.gsm.prof_androidv2

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.gsm.prof_androidv2.databinding.PhotoRecyclerviewItemBinding
import com.gsm.prof_androidv2.databinding.ProjectRecyclerViewItemBinding
import com.gsm.prof_androidv2.model.dto.GetCategoryPostDto
import com.gsm.prof_androidv2.view.Type
import com.gsm.prof_androidv2.view.viewpager.ViewPagerFragmentDirections
import com.gsm.prof_androidv2.viewmodel.MainViewModel
import kotlin.properties.Delegates


class PhotoAdapter(
    private val context: Context,

    private val getCategoryPostDto: GetCategoryPostDto
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val response: MutableList<String> = mutableListOf()
    val storage = FirebaseStorage.getInstance();
    private val storageReference = storage.reference


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {


        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<PhotoRecyclerviewItemBinding>(
            layoutInflater,
            R.layout.photo_recyclerview_item,
            parent,
            false
        )
        return PhotoRecyclerViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder: ${response[position]}")
        when (holder) {

            (holder as PhotoRecyclerViewHolder) -> {
                (holder as? PhotoRecyclerViewHolder)?.bind(getCategoryPostDto)
                val pathReference = storageReference.child(getCategoryPostDto.photo.toString())

                if (pathReference == null) {
                    Toast.makeText(context, "저장소에 사진이 없습니다.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val submitProfile = storageReference.child(getCategoryPostDto.photo.toString())
                    submitProfile.downloadUrl
                        .addOnSuccessListener {

                            Glide.with(context).load(it).into(holder.bindingPhoto.imagePhoto);

                        }
                }


            }
        }

    }

    override fun getItemCount(): Int {
        return response.size
    }
}


class PhotoRecyclerViewHolder(val bindingPhoto: PhotoRecyclerviewItemBinding) :
    RecyclerView.ViewHolder(bindingPhoto.root) {
    fun bind(data: GetCategoryPostDto) {
        bindingPhoto.data = data
        bindingPhoto.executePendingBindings()
    }
}

