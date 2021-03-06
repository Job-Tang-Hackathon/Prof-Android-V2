package com.gsm.prof_androidv2.viewmodel

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.gsm.prof_androidv2.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import com.google.firebase.storage.StorageReference
import com.gsm.prof_androidv2.model.dto.UploadDto
import com.gsm.prof_androidv2.utils.Utils.storageURI
import kotlin.collections.ArrayList


enum class ActionType {
    PLUS, MINUS
}

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val database: FirebaseFirestore
) : ViewModel() {

    private val _imgs = MutableLiveData<MutableList<Uri>>()
    private val _photocnt = MutableLiveData<Int>()
    private val _cnt = MutableLiveData<Int>()
    private val _loadingToast = MutableLiveData<Event<Boolean>>()
    private val _visible = MutableLiveData<Boolean>()
    private val _visibleBtn = MutableLiveData<Boolean>()
    private val _loadingText = MutableLiveData<String>()
    private val _title = MutableLiveData<UploadDto>()
    private val _tag = MutableLiveData<UploadDto>()
    private val _state = MutableLiveData<UploadDto>()
    private val _photoUrl = ArrayList<String>()

    val photoUri: ArrayList<String> = _photoUrl
    val title: LiveData<UploadDto> = _title
    val tag: LiveData<UploadDto> = _tag
    val state: LiveData<UploadDto> = _state
    val loadingText: LiveData<String> = _loadingText
    val loadingToast: LiveData<Event<Boolean>> = _loadingToast
    val visible: LiveData<Boolean> = _visible
    val visibleBtn: LiveData<Boolean> = _visibleBtn
    val cnt: LiveData<Int> = _cnt
    val imgs: LiveData<MutableList<Uri>>
        get() = _imgs

    val photocnt: LiveData<Int>
        get() = _photocnt

    init {
        _imgs.value = mutableListOf()
        _photocnt.value = 0
        _cnt.value = 0
        _visible.value = false
        _visibleBtn.value = true
        _loadingText.value = ""
    }

    fun photoCount(actionType: ActionType, cnt: Int) {
        when (actionType) {
            ActionType.PLUS -> {
                _photocnt.value = _photocnt.value?.plus(cnt)
            }
            ActionType.MINUS -> {
                _photocnt.value = _photocnt.value?.minus(cnt)
            }

        }
    }

    fun deleteImg(index: Int) {
        _imgs.value?.removeAt(index)
    }

    fun setImg(photo: Uri) {
        _imgs.value?.add(photo)
    }

    fun progress() {
        _visible.value = true
    }

    fun imgUpLoad(
        formatter: SimpleDateFormat,
        uid: String
    ) {
        _visibleBtn.value = false
        if (_imgs.value != null) {
            val filename = formatter.format(Date()).toString() + ".png"
            val storageRef: StorageReference =
                firebaseStorage.getReferenceFromUrl("$storageURI").child(
                    "images/$uid/$filename"
                )
            _photoUrl.add("images/$uid/$filename")
            _imgs.value!!.let {
                storageRef.putFile(it[_cnt.value!!])
                    .addOnSuccessListener {
                        _loadingText.value = "????????? ??????!"
                        _loadingToast.value = Event(true)
                        _visible.value = false
                        _cnt.value = _cnt.value?.plus(1)
                    }
                    .addOnFailureListener {
                        _loadingText.value = "????????? ??????"
                        _loadingToast.value = Event(true)
                        _visible.value = false
                        _visibleBtn.value = true
                        Log.d(TAG, "imgUpLoad:$it ")

                    }
            }

        } else {
            _loadingText.value = "????????? ???????????????"
            _loadingToast.value = Event(true)
            _visible.value = false
            _visibleBtn.value = true
        }
    }

    fun storeUpload(
        title: String,
        tag: String,
        people: String,
        oneLine: String,
        fullLine: String,
        link: String,
        state: String,
        category: String, uid: String
    ) {

        if (title != "" && tag != "" && people != "" && oneLine != "" && fullLine != "" && state != "") {
            val dataInput = UploadDto(
                fullLine,
                oneLine,
                people,
                photoUri,
                state,
                tag,
                title,
                link,
                uid
            )
            database.collection("all").add(dataInput)
            database.collection("${category}").add(dataInput)
        }
    }

}