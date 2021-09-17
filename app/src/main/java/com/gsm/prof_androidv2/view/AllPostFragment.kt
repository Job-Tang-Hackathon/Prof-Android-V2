package com.gsm.prof_androidv2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.FragmentAllPostBinding
import com.gsm.prof_androidv2.utils.showVertical
import com.gsm.prof_androidv2.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllPostFragment : Fragment() {
    lateinit var binding: FragmentAllPostBinding
    private val mainViewModel by activityViewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_post, container, false)
        observeViewModel()

        return binding.root
    }


    private fun initRecyclerView() {
        binding.allPostRecyclerView.showVertical(requireContext())
        binding.allPostRecyclerView.adapter = MainRecyclerAdapter(requireContext(), Type.ALL, mainViewModel)
    }


    private fun observeViewModel() {
        mainViewModel.getPostResponse.observe(requireActivity(), androidx.lifecycle.Observer {
            //게시물 있음
            if (it != null) {
                for (document in it.result) {
                    initRecyclerView()
                    mainViewModel.setGetAllPostNull(false)
                }
            } else {
                //게시물 없음
                mainViewModel.setGetAllPostNull(true)
            }
        })

        mainViewModel.message.observe(requireActivity(), Observer {
            when (it) {
                "blank" -> Toast.makeText(requireContext(), "검색된 게시물이 없습니다", Toast.LENGTH_SHORT)
                    .show()
                "error" -> Toast.makeText(requireContext(), "서버에 오류가 발생했습니다", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        mainViewModel.getAllPostNull.observe(requireActivity(), androidx.lifecycle.Observer {
            if (it == true) {
                binding.allPostRecyclerView.visibility = View.GONE
                binding.notFound.visibility = View.VISIBLE
            } else {
                binding.allPostRecyclerView.visibility = View.VISIBLE
                binding.notFound.visibility = View.GONE
            }
        })
    }
}