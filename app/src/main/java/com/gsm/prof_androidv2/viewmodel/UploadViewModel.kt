package com.gsm.prof_androidv2.viewmodel

import android.app.Application
import android.net.Uri
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

enum class ActionType{
    PLUS,MINUS
}

@HiltViewModel
class UploadViewModel @Inject constructor() :ViewModel()  {

    private val _imgs = MutableLiveData<MutableList<Uri>>()
    private val _photocnt = MutableLiveData<Int>()

    val imgs : LiveData<MutableList<Uri>>
        get() = _imgs

    val photocnt : LiveData<Int>
        get() = _photocnt

    init {
        _imgs.value = mutableListOf()
        _photocnt.value = 0
    }

    fun photoCount(actionType: ActionType,cnt : Int){
        when(actionType){
            ActionType.PLUS -> {
                _photocnt.value = _photocnt.value?.plus(cnt)
            }
            ActionType.MINUS -> {
                _photocnt.value = _photocnt.value?.minus(cnt)
            }

        }
    }

    fun deleteImg(index:Int){
        _imgs.value?.removeAt(index)
    }

    fun setImg(photo:Uri){
        _imgs.value?.add(photo)
    }



}