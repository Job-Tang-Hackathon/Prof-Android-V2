package com.gsm.prof_androidv2.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsm.prof_androidv2.PhotoAdapter
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.FragmentAllDetailBinding
import com.gsm.prof_androidv2.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentDetail : Fragment() {

    lateinit var binding: FragmentAllDetailBinding


    private val args by navArgs<FragmentDetailArgs>()

    private val mainViewModel by activityViewModels<MainViewModel>()

    private val photoAdapter: MainRecyclerAdapter by lazy {
        MainRecyclerAdapter(requireContext(), Type.PHOTO, mainViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_detail, container, false)
        setAdapter()
        (activity as MainActivity).binding.apply {
            this.spinnerLayout.visibility = GONE
            this.searchLayout.visibility = GONE
            this.spinnerLayout.visibility=GONE
            this.searchLayout.visibility=GONE
            this.fabBack.visibility= GONE
            this.fabUpload.visibility= GONE
        }
        binding.data = args.allData
        Log.d("TAG", "onCreateView: ${args.allData.uid}")
        return binding.root
    }


    fun setAdapter() {

        binding.recyclerView.apply {
            this.adapter = photoAdapter
            this.layoutManager =
                GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
        }
    }
}