package com.gsm.prof_androidv2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.gsm.prof_androidv2.model.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: FirebaseRepository
): ViewModel() {
    val getPostResponse : LiveData<Task<QuerySnapshot>> get() = _getPostResponse
    private val _getPostResponse: MutableLiveData<Task<QuerySnapshot>> = MutableLiveData<Task<QuerySnapshot>>()

    val getPostNull : LiveData<Boolean> get() = _getPostNull
    private val _getPostNull: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun getCategoryPost(state:String) = repository.getCategoryPost(state)

    //가져온 게시물
    fun setGetPostResponse(response: Task<QuerySnapshot>){
        _getPostResponse.value = response
    }

    //게시물이 없는지 체크 true = 게시물 없음, false = 게시물 있음
    fun setGetPostNull(check : Boolean){
        _getPostNull.value = check
    }
}