package com.gsm.prof_androidv2.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.ProjectRecyclerViewItemBinding
import com.gsm.prof_androidv2.model.dto.GetCategoryPostDto
import com.gsm.prof_androidv2.view.viewpager.ViewPagerFragmentDirections
import com.gsm.prof_androidv2.viewmodel.MainViewModel
import kotlin.properties.Delegates

class MyPostRecyclerAdapter (
    private val viewModel : MainViewModel
) : RecyclerView.Adapter<MyPostRecyclerViewHolder>() {
    var response : ArrayList<GetCategoryPostDto> = arrayListOf()
    var count by Delegates.notNull<Int>()
    private var item : GetCategoryPostDto? = null


    init {
        response.clear()
        val snapshots = viewModel.getMyPostResponse.value
        if (snapshots != null) {
            for (document in snapshots) {
                item = document.toObject(GetCategoryPostDto::class.java)
                response.add(item!!)
            }
        }
        count = viewModel.getMyPostResponse.value!!.size()
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

        holder.itemView.setOnClickListener {
            val action = ViewPagerFragmentDirections.actionViewPagerFragmentToFragmentAllDetail(
                response[position]
            )
            it.findNavController().navigate(action)
        }
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