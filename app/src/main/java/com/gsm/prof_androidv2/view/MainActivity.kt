package com.gsm.prof_androidv2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.gsm.prof_androidv2.R
import com.gsm.prof_androidv2.databinding.ActivityMainBinding
import com.gsm.prof_androidv2.view.upload.ProjectUploadActivity
import com.gsm.prof_androidv2.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var tag = ""
    private val mainViewModel by viewModels<MainViewModel>()
    private val userUid: String? = FirebaseAuth.getInstance().uid


    override fun onStart() {
        super.onStart()
        Log.d("로그", "인텐트에서 받아온 정보 : ${intent.getStringExtra("state")}")
        intent.getStringExtra("state")?.let { getCategoryPost(it) }
        intent.getStringExtra("state")?.let { getMyPost(it, userUid!!) }
        val allPostFragment = AllPostFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.activity = this
        binding.postCount.visibility = View.VISIBLE
        setupSpinnerTag()
        setupSpinnerHandler()
        firstSpinner()
    }

    fun upload(){
        val intent = Intent(this,ProjectUploadActivity::class.java)
        startActivity(intent)
    }

    fun backBtn(){
        finish()
    }


    private fun firstSpinner() {
        val state: Int = when (intent.getStringExtra("state")) {
            "android" -> 1
            "ios" -> 2
            "frontend" -> 3
            "backend" -> 4
            "ai" -> 5
            "game" -> 6
            "iot" -> 7
            "infosecurity" -> 8
            "robotics" -> 9
            else -> 0
        }
        binding.spinner.setSelection(state)
    }


    //선택한 카테고리의 모든 게시물 가져오기
    private fun getCategoryPost(state: String) {
        mainViewModel.getCategoryPost(state)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.postCount.text = "총 ${task.result.size()}개의 항목"
                    mainViewModel.setAllPostCount(task.result.size())

                    mainViewModel.setGetPostResponse(task)
                    if (task.result.documents.toString() == "[]") {
                        mainViewModel.setGetAllPostNull(true)
                    }
                } else mainViewModel.setMessage("error")
            }
            .addOnFailureListener { exception ->
                mainViewModel.setMessage("error")
            }
    }


    //선택한 카테고리의 내 게시물 가져오기
    private fun getMyPost(state: String, uid: String) {
        var id = ""
        mainViewModel.getMyPost(state, uid)
            .addOnSuccessListener {
                for (document in it) {
                    id = document.id
                }
                if (id == "[]") mainViewModel.setGetAllPostNull(true)
                else mainViewModel.setGetMyPostResponse(it)
            }
            .addOnFailureListener { exception ->
                mainViewModel.setMessage("error")
            }
    }


    //검색한 게시물 가져오기
    fun getSearchedPost() {
        var keyword = binding.searchBar.text.toString()
        if (keyword.isEmpty()) {
            Toast.makeText(this, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        //전체 게시물 검색
        mainViewModel.getSearchedPost(keyword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var response = ""
                    for (document in task.result) {
                        response = document.id
                    }
                    if (response == "[]" || response == "") mainViewModel.setMessage("blank")
                    else mainViewModel.setGetPostResponse(task)
                }
            }
            .addOnFailureListener { exception ->
                mainViewModel.setMessage("error")
            }

        //내 게시물중 검색
        if (userUid != null) {
            mainViewModel.getSearchMyPost(keyword, userUid)
                .addOnSuccessListener {
                    var response = ""
                    for (document in it) {
                        response = document.id
                    }
                    if (response == "[]" || response == "") {

                    } else mainViewModel.setGetMyPostResponse(it)
                }
                .addOnFailureListener { exception ->
                    mainViewModel.setMessage("error")
                }
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
                var state = "all"
                when (position) {
                    0 -> {
                        tag = "전체"
                        state = "all"
                    }
                    1 -> {
                        tag = "안드로이드"
                        state = "android"
                    }
                    2 -> {
                        tag = "iOS"
                        state = "ios"
                    }
                    3 -> {
                        tag = "프론트엔드"
                        state = "frontend"
                    }
                    4 -> {
                        tag = "백엔드"
                        state = "backend"
                    }
                    5 -> {
                        tag = "인공지능"
                        state = "ai"
                    }
                    6 -> {
                        tag = "게임개발"
                        state = "game"
                    }
                    7 -> {
                        tag = "IoT"
                        state = "iot"
                    }
                    8 -> {
                        tag = "정보보안"
                        state = "infosecurity"
                    }
                    9 -> {
                        tag = "로보틱스"
                        state = "robotics"
                    }
                    else -> {
                        tag = "전체"
                        state = "all"
                    }
                }
                getCategoryPost(state)
                getMyPost(state, userUid!!)
                binding.searchBar.setText("")
            }
        }

    }


}