package com.gsm.prof_androidv2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.ActivityMainBinding
import com.gsm.prof_androidv2.utils.showVertical
import com.gsm.prof_androidv2.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var tag = ""
    private val mainViewModel by viewModels<MainViewModel>()


    override fun onStart() {
        super.onStart()
        Log.d("로그","인텐트에서 받아온 정보 : ${intent.getStringExtra("state")}")
        intent.getStringExtra("state")?.let { getCategoryPost(it) }
        //getCategoryPost("XlvjmPpeAQX6QzlUDMsqVrx4YHD3")
        val allPostFragment = AllPostFragment()
        val bundle = bundleOf("state" to intent.getStringExtra("state"))
        allPostFragment.arguments = bundle
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupSpinnerTag()
        setupSpinnerHandler()
        observeViewModel()
        initViewPagerTabLayout()
    }

    private fun initViewPagerTabLayout(){
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

    private fun observeViewModel(){
        mainViewModel.getPostResponse.observe(this, Observer {

        })
    }

    //Post 가져오기
    private fun getCategoryPost(state:String){
        mainViewModel.getCategoryPost(state)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mainViewModel.setGetPostResponse(task)
                    for (document in task.result) {
                        Log.d("로그", document.id + " => " + document.data + "오우야 : "+task.result)

                    }
                }
            }
          /*  .addOnSuccessListener { document ->
                for ()
                if (document != null) {
                    mainViewModel.setGetPostResponse(document)
                    Log.d("그그","$document")
                } else {
                    Toast.makeText(this,"서버에 오류가 발생했습니다",Toast.LENGTH_SHORT).show()
                }
            }*/
            .addOnFailureListener { exception ->
                Toast.makeText(this,"서버에 오류가 발생했습니다",Toast.LENGTH_SHORT).show()
            }
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