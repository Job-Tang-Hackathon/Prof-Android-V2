package com.gsm.prof_androidv2.view.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.FragmentViewpagerBinding
import com.gsm.prof_androidv2.view.MainActivity

class ViewPagerFragment : Fragment() {

    lateinit var binding : FragmentViewpagerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_viewpager, container, false)



        return binding.root
    }

 

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPagerTabLayout()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).binding.apply {
            this.searchLayout.visibility= VISIBLE
            this.spinnerLayout.visibility=VISIBLE
            this.fabBack.visibility= VISIBLE
            this.fabUpload.visibility= VISIBLE
        }
    }

    private fun initViewPagerTabLayout(){
        val adapter = ViewPagerAdapter(requireActivity())
        binding.viewPager2.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "전체 게시물"
                }
                1 -> {
                    tab.text = "내 게시물"
                }
            }
        }.attach()
    }


}