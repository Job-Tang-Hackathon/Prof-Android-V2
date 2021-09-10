package com.gsm.prof_androidv2.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.ProjectRecyclerViewItemBinding
import com.gsm.prof_androidv2.model.dto.GetCategoryPostDto
import com.gsm.prof_androidv2.viewmodel.MainViewModel
import kotlin.properties.Delegates

class MyPostRecyclerAdapter (
    private val viewModel : MainViewModel
) : RecyclerView.Adapter<MyPostRecyclerViewHolder>() {
    var response : ArrayList<GetCategoryPostDto> = arrayListOf()
    var count by Delegates.notNull<Int>()
    private val uid  = FirebaseAuth.getInstance().uid

    init {
        response.clear()
        count = 0
        for (snapshot in viewModel.getPostResponse.value!!.result.documents) {
            if (snapshot.id == uid){
                var item = snapshot.toObject(GetCategoryPostDto::class.java)
                response.add(item!!)
                ++count
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPostRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<ProjectRecyclerViewItemBinding>(
            layoutInflater,
            R.layout.project_recycler_view_item,
            parent,
            false
        )
        return MyPostRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPostRecyclerViewHolder, position: Int) {
        (holder as? MyPostRecyclerViewHolder)?.bind(response[position])

    }

    override fun getItemCount(): Int {
        return count
    }
}

class MyPostRecyclerViewHolder(val binding: ProjectRecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: GetCategoryPostDto) {
        binding.data = data
        binding.executePendingBindings()
    }
}