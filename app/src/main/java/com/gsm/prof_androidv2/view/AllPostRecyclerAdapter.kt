package com.gsm.prof_androidv2.view

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
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.PhotoRecyclerviewItemBinding
import com.gsm.prof_androidv2.databinding.ProjectRecyclerViewItemBinding
import com.gsm.prof_androidv2.model.dto.GetCategoryPostDto
import com.gsm.prof_androidv2.view.viewpager.ViewPagerFragmentDirections
import com.gsm.prof_androidv2.viewmodel.MainViewModel
import kotlin.properties.Delegates

enum class Type {
    ALL, PHOTO
}

class MainRecyclerAdapter(
    private val context: Context,
    private val type: Type,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var response: ArrayList<GetCategoryPostDto> = arrayListOf()
    var count by Delegates.notNull<Int>()
    val storage = FirebaseStorage.getInstance();
    private val storageReference = storage.reference


    init {
        response.clear()
        count = viewModel.getPostResponse.value?.result?.size()!!
        for (snapshot in viewModel.getPostResponse.value!!.result.documents) {
            var item = snapshot.toObject(GetCategoryPostDto::class.java)
            response.add(item!!)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {


        when (type) {
            Type.ALL -> {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = DataBindingUtil.inflate<ProjectRecyclerViewItemBinding>(
                    layoutInflater,
                    R.layout.project_recycler_view_item,
                    parent,
                    false
                )
                return MainRecyclerViewHolder(binding)
            }

            Type.PHOTO -> {

                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = DataBindingUtil.inflate<PhotoRecyclerviewItemBinding>(
                    layoutInflater,
                    R.layout.photo_recyclerview_item,
                    parent,
                    false
                )
                return PhotoRecyclerViewHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (type) {
            Type.ALL -> {
                (holder as MainRecyclerViewHolder).bind(response[position])

                holder.itemView.setOnClickListener {
                    val action =
                        ViewPagerFragmentDirections.actionViewPagerFragmentToFragmentAllDetail(
                            response[position]
                        )
                    it.findNavController().navigate(action)
                }
            }

            Type.PHOTO -> {

                (holder as PhotoRecyclerViewHolder).bind(response[position])
                val pathReference = storageReference.child(response[position].photo.toString())

                if (pathReference == null) {
                    Toast.makeText(context, "저장소에 사진이 없습니다.", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    Log.d("TAG", "onBindViewHolder: ${response[position].photo.size}")
                    for (i in response[position].photo.indices) {



                        Log.d("TAG", "onBindViewHolder: $i")
                        val submitProfile = storageReference.child(response[position].photo[i])
                        submitProfile.downloadUrl
                            .addOnSuccessListener {

                                Glide.with(context).load(it).into(holder.bindingPhoto.imagePhoto)

                            }
                    }
                }


            }
            else-> null
        }

    }

    override fun getItemCount(): Int {
        return count
    }
}

class MainRecyclerViewHolder(val binding: ProjectRecyclerViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: GetCategoryPostDto) {
        binding.data = data
        binding.executePendingBindings()
    }
}

class PhotoRecyclerViewHolder(val bindingPhoto: PhotoRecyclerviewItemBinding) :
    RecyclerView.ViewHolder(bindingPhoto.root) {
    fun bind(data: GetCategoryPostDto) {
        bindingPhoto.data = data
        bindingPhoto.executePendingBindings()
    }
}