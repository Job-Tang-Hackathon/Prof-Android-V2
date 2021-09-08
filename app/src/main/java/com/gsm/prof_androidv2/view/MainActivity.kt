package com.gsm.prof_androidv2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.ActivityMainBinding
import com.gsm.prof_androidv2.utils.showVertical

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var tag = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
        setupSpinnerTag()
        setupSpinnerHandler()

        val adapter = ViewPagerAdapter(this)
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

    private fun initRecyclerView(){
        binding.recyclerView.showVertical(this)
        binding.recyclerView.adapter = MainRecyclerAdapter()
    }



    private fun setupSpinnerTag() {
        binding.spinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.MainListItem,
            R.layout.main_spinner_item
        )
    }

    private fun setupSpinnerHandler() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        tag = "전체"
                    }
                    1 -> {
                        tag = "안드로이드"

                    }
                    2 -> {
                        tag = "iOS"

                    }
                    3 -> {
                        tag = "프론트엔드"

                    }
                    4 -> {
                        tag = "백엔드"

                    }
                    5 -> {
                        tag = "인공지능"

                    }
                    6 -> {
                        tag = "게임개발"

                    }
                    7 -> {
                        tag = "IoT"

                    }
                    8 -> {
                        tag = "정보보안"

                    }
                    9 -> {
                        tag = "로보틱스"

                    }
                    else -> {
                        tag = "전체"
                    }
                }
            }
        }

    }

}