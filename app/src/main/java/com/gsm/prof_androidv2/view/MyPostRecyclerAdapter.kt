package com.gsm.prof_androidv2.view

import android.util.Log
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
    private var item : GetCategoryPostDto? = null

    init {
        response.clear()
        val snapshots = viewModel.getMyPostResponse.value

        if (snapshots != null) {
            for (document in snapshots) {
                Log.i("로그", "어뎁터 안 : ${document.id} => ${document.data}")
                item = document.toObject(GetCategoryPostDto::class.java)
                response.add(item!!)
            }
        }
        Log.d("로그","어뎁터 안의 mypost 값 : $")
        count = viewModel.getMyPostResponse.value!!.size()
/*        if (snapshots != null) {
            for (snapshot in snapshots) {
                if (snapshot.id == uid){
                    item = snapshot.toObject(GetCategoryPostDto::class.java)
                    response.add(item!!)
                    //++count
                    Log.d("로그점","count : $count, item : $item, response : $response")
                }

            }
        }*/
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