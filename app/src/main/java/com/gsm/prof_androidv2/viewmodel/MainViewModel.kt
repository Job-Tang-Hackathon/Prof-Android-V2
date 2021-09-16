package com.gsm.prof_androidv2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.gsm.prof_androidv2.model.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: FirebaseRepository
): ViewModel() {
    //선택한 카테고리의 모든 게시물 저장
    val getPostResponse : LiveData<Task<QuerySnapshot>> get() = _getPostResponse
    private val _getPostResponse: MutableLiveData<Task<QuerySnapshot>> = MutableLiveData<Task<QuerySnapshot>>()

    //선택한 카테고리의 내 게시물 저장
    val getMyPostResponse : LiveData<QuerySnapshot> get() = _getMyPostResponse
    private val _getMyPostResponse = MutableLiveData<QuerySnapshot>()

    val getAllPostNull : LiveData<Boolean> get() = _getAllPostNull
    private val _getAllPostNull: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val getMyPostNull : LiveData<Boolean> get() = _getMyPostNull
    private val _getMyPostNull: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    //전체 게시물 개수
    val allPostCount : LiveData<Int> get() = _allPostCount
    private val _allPostCount = MutableLiveData<Int>()

    //내 게시물 개수
    val myPostCount : LiveData<Int> get() = _myPostCount
    private val _myPostCount = MutableLiveData<Int>()

    //선택한 카테고리의 모든 게시물 가져오기
    fun getCategoryPost(state:String) = repository.getCategoryPost(state)

    //선택한 카테고리의 내 게시물 가져오기
    fun getMyPost(state: String, uid: String) = repository.getMyPost(state,uid)

    //선택한 카테고리의 가져온 모든 게시물 저장
    fun setGetPostResponse(response: Task<QuerySnapshot>){
        _getPostResponse.value = response
    }

    //선택한 카테고리의 내 게시물 저장
    fun setGetMyPostResponse(response: QuerySnapshot){
        _getMyPostResponse.value = response
    }



    //게시물이 없는지 체크 true = 게시물 없음, false = 게시물 있음
    fun setGetAllPostNull(check : Boolean){
        _getAllPostNull.value = check
    }

    //게시물이 없는지 체크 true = 게시물 없음, false = 게시물 있음
    fun setGetMyPostNull(check : Boolean){
        _getMyPostNull.value = check
    }

    fun setAllPostCount(count : Int){
        _allPostCount.value = count
    }

    fun setMyPostCount(count : Int){
        _myPostCount.value = count
    }
}