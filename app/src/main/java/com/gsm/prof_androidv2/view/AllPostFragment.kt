package com.gsm.prof_androidv2.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.FragmentAllPostBinding
import com.gsm.prof_androidv2.utils.showVertical
import com.gsm.prof_androidv2.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllPostFragment : Fragment() {
    lateinit var binding : FragmentAllPostBinding
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val TAG = "AllPost"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_all_post,container,false)
        observeViewModel()

        return binding.root
    }

    private fun initRecyclerView(){
        binding.allPostRecyclerView.showVertical(requireContext())
        binding.allPostRecyclerView.adapter = MainRecyclerAdapter(mainViewModel)
    }

    private fun observeViewModel(){
        mainViewModel.getPostResponse.observe(requireActivity(), androidx.lifecycle.Observer {
            //게시물 있음
            if (it !=null){
                for (document in it.result) {
                    Log.d("로그","옵져버 : ${document.data}")
                    initRecyclerView()
                    mainViewModel.setGetAllPostNull(false)
                }
            }else{
                //게시물 없음
                Log.d("로그","옵져버 널")
                mainViewModel.setGetAllPostNull(true)
            }

        })

        mainViewModel.getAllPostNull.observe(requireActivity(), androidx.lifecycle.Observer {
            if(it == true){
                binding.allPostRecyclerView.visibility = View.GONE
                binding.notFound.visibility = View.VISIBLE
            }else{
                binding.allPostRecyclerView.visibility = View.VISIBLE
                binding.notFound.visibility = View.GONE
            }
        })
    }
}