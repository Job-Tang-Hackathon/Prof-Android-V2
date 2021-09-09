package com.gsm.prof_androidv2.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.FragmentAllPostBinding
import com.gsm.prof_androidv2.utils.showVertical
import com.gsm.prof_androidv2.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Observer

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

/*    //Post 가져오기
    private fun getCategoryPost(state:String){
        Log.d("로그","getCategoryPost 안")
        mainViewModel.getCategoryPost(state)
            .addOnSuccessListener { document ->
                if (document != null) {
                    mainViewModel.setGetPostResponse(document)
                    Log.d("그그","$document")
                } else {
                    Toast.makeText(requireContext(),"서버에 오류가 발생했습니다",Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(),"서버에 오류가 발생했습니다",Toast.LENGTH_SHORT).show()
            }
    }*/


    private fun observeViewModel(){
        mainViewModel.getPostResponse.observe(requireActivity(), androidx.lifecycle.Observer {
            if (it !=null){
                for (document in it.result) {
                    Log.d("로그","옵져버 : ${document.data}")
                    initRecyclerView()
                }
            }else{
                Log.d("로그","옵져버 널")

            }

        })

        mainViewModel.getPostNull.observe(requireActivity(), androidx.lifecycle.Observer {
            if(it == true){
                binding.notFound.visibility = View.VISIBLE
            }
        })
    }
}