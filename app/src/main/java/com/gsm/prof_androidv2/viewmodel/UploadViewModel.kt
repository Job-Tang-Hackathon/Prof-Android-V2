package com.gsm.prof_androidv2.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.net.Uri
import android.util.EventLog
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.gsm.prof_androidv2.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import com.google.firebase.storage.StorageReference
import com.gsm.prof_androidv2.utils.Utils.storageURI


enum class ActionType{
    PLUS,MINUS
}

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val firebaseStorage: FirebaseStorage
) :ViewModel()  {

    private val _imgs = MutableLiveData<MutableList<Uri>>()
    private val _photocnt = MutableLiveData<Int>()
    private val _cnt = MutableLiveData<Int>()
    private val _loadingToast = MutableLiveData<Event<Boolean>>()
    private val _visible = MutableLiveData<Boolean>()
    private val _loadingText = MutableLiveData<String>()

    val loadingText : LiveData<String> = _loadingText
    val loadingToast : LiveData<Event<Boolean>> = _loadingToast
    val visible : LiveData<Boolean> = _visible
    val cnt : LiveData<Int> = _cnt
    val imgs : LiveData<MutableList<Uri>>
        get() = _imgs

    val photocnt : LiveData<Int>
        get() = _photocnt

    init {
        _imgs.value = mutableListOf()
        _photocnt.value = 0
        _cnt.value = 0
        _visible.value = false
        _loadingText.value = ""
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

    fun progress() {
        _visible.value = true
    }

    fun imgUpLoad(formatter: SimpleDateFormat) {
        if(_imgs.value != null){
            val filename = formatter.format(Date()).toString() + ".png"
            val storageRef: StorageReference =
                firebaseStorage.getReferenceFromUrl("$storageURI").child(
                    "images/$filename"
                )
            _imgs.value!!.let {
                storageRef.putFile(it[_cnt.value!!])
                    .addOnSuccessListener {
                        _loadingText.value = "업로드 성공!"
                        _loadingToast.value = Event(true)
                        _visible.value = false
                        _cnt.value = _cnt.value?.plus(1)
                    }
                    .addOnFailureListener{
                        _loadingText.value = "업로드 실패"
                        _loadingToast.value = Event(true)
                        _visible.value = false
                        Log.d(TAG, "imgUpLoad:$it ")
                    }
            }

        }else{
            _loadingText.value = "사진을 지정해주세요"
            _loadingToast.value = Event(true)
            _visible.value = false
        }
    }


}