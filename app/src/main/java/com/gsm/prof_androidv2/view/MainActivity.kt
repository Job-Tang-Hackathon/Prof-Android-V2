package com.gsm.prof_androidv2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gsm.prof_androidv2.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupSpinnerTag()
        setupSpinnerHandler()
        firstSpinner()
        observeViewModel()
        binding.postCount.visibility = View.VISIBLE
    }

    private fun firstSpinner(){
        val state : Int = when(intent.getStringExtra("state")){
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



    private fun observeViewModel(){
        mainViewModel.getPostResponse.observe(this, Observer {

        })
    }

    //선택한 카테고리의 모든 게시물 가져오기
    private fun getCategoryPost(state:String){
        mainViewModel.getCategoryPost(state)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task != null){
                        binding.postCount.text = "총 ${task.result.size()}개의 항목"
                        mainViewModel.setAllPostCount(task.result.size())

                        mainViewModel.setGetPostResponse(task)
                        if (task.result.documents.toString() == "[]") {
                            mainViewModel.setGetAllPostNull(true)
                        }
                    }else{
                        Log.d("로그","else  task != null")
                    }
                }else{
                    Log.d("로그","isSuccessful")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("로그","addOnFailureListener : $exception")
                Toast.makeText(this,"서버에 오류가 발생했습니다",Toast.LENGTH_SHORT).show()
            }
    }

    //선택한 카테고리의 내 게시물 가져오기
    private fun getMyPost(state:String, uid : String){
        Log.d("로그","내 게시물을 가져오라고 시킴 - state : $state, uid : $uid")
        mainViewModel.getMyPost(state, uid)
            /*    .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("로그","getMyPost : ${task.result.size()}, ${task.result.isEmpty}, ${task.result.}")
                        mainViewModel.setGetMyPostResponse(task)
                        binding.postCount.text = "총 ${task.result.size()}개의 항목"
                        mainViewModel.setAllPostCount(task.result.size())
                    }else{
                        Log.d("로그","isSuccessful")
                    }
                }*/
            .addOnSuccessListener {
                var id = ""
                for (document in it) {
                    Log.i("로그", "${document.id} => ${document.data}")
                    id = document.id
                }
                Log.d("로그","getMyPostOnSuccess - $it, ${it.size()}, ${it.metadata}, ${it.isEmpty}, $id")
                if (id=="[]"){
                    mainViewModel.setGetAllPostNull(true)
                    Log.d("로그","여기 1")
                }else{
                    mainViewModel.setGetMyPostResponse(it)
                    Log.d("로그","여기 2")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("로그","addOnFailureListener : $exception")
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
            }
        }

    }

}