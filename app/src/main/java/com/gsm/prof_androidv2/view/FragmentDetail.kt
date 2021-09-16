package com.gsm.prof_androidv2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.FragmentAllDetailBinding

class FragmentDetail : Fragment() {

    lateinit var binding: FragmentAllDetailBinding
    private val args by navArgs<FragmentDetailArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_detail, container, false)
        (activity as MainActivity).binding.apply {
            this.spinnerLayout.visibility=GONE
            this.searchLayout.visibility=GONE
        }
        binding.data = args.allData

        return binding.root
    }
}