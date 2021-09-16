package com.gsm.prof_androidv2.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.ProjectRecyclerViewItemBinding
import com.gsm.prof_androidv2.model.dto.GetCategoryPostDto
import com.gsm.prof_androidv2.model.repository.FirebaseRepository
import com.gsm.prof_androidv2.viewmodel.MainViewModel
import javax.inject.Inject
import kotlin.properties.Delegates

class MainRecyclerAdapter(
  viewModel : MainViewModel
) : RecyclerView.Adapter<MainRecyclerViewHolder>() {
    var response : ArrayList<GetCategoryPostDto> = arrayListOf()
    var count by Delegates.notNull<Int>()

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
    ): MainRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<ProjectRecyclerViewItemBinding>(
            layoutInflater,
            R.layout.project_recycler_view_item,
            parent,
            false
        )
        return MainRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
        (holder as? MainRecyclerViewHolder)?.bind(response[position])

    }

    override fun getItemCount(): Int {
        return count
    }
}

class MainRecyclerViewHolder(val binding: ProjectRecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: GetCategoryPostDto) {
        binding.data = data
        binding.executePendingBindings()
    }
}