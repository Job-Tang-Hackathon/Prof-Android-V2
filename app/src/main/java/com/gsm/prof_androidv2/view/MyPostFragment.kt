package com.gsm.prof_androidv2.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.FragmentMyPostBinding
import com.gsm.prof_androidv2.model.dto.GetCategoryPostDto
import com.gsm.prof_androidv2.utils.showVertical
import com.gsm.prof_androidv2.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPostFragment : Fragment() {
    lateinit var binding : FragmentMyPostBinding
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val uid  = FirebaseAuth.getInstance().uid
    var response : ArrayList<GetCategoryPostDto> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_post,container,false)
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel(){
        if (mainViewModel.getMyPostNull.value == true){
            binding.myPostRecyclerView.visibility = View.GONE
            binding.notFound.visibility = View.VISIBLE
        }
        mainViewModel.getMyPostResponse.observe(requireActivity(), androidx.lifecycle.Observer {
            //게시물 있음
            Log.d("하","$it, ${it.documents}")
            if (!it.isEmpty){
                Log.d("로그","여기 MyPostFragment 1")
                initRecyclerView()
                mainViewModel.setGetMyPostNull(false)

            }else{
                //게시물 없음
                Log.d("로그","여기 MyPostFragment 2")
                mainViewModel.setGetMyPostNull(true)
            }
        })


        mainViewModel.message.observe(requireActivity(), Observer {
            when(it){
                "blank" -> Toast.makeText(requireContext(),"검색된 게시물이 없습니다",Toast.LENGTH_SHORT).show()
            }
        })
 /*       mainViewModel.getSearchedPostResponse.observe(requireActivity(), Observer {

        })
        */
        mainViewModel.getMyPostNull.observe(requireActivity(), androidx.lifecycle.Observer {
            if(it == true){
                Log.d("로그","여기 getMyPostNull 1")
                binding.myPostRecyclerView.visibility = View.GONE
                binding.notFound.visibility = View.VISIBLE
            }else{
                Log.d("로그","여기 getMyPostNull 2")
                binding.myPostRecyclerView.visibility = View.VISIBLE
                binding.notFound.visibility = View.GONE
            }
        })
    }


    private fun initRecyclerView(){
        binding.myPostRecyclerView.showVertical(requireContext())
        binding.myPostRecyclerView.adapter = MyPostRecyclerAdapter(mainViewModel)
    }
}