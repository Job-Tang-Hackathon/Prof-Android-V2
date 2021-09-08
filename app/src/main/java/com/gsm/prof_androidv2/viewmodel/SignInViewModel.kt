package com.gsm.prof_androidv2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val auth : FirebaseAuth
)  : ViewModel() {

    val signInResponse : LiveData<Int> get() = _signInResponse
    private val _signInResponse: MutableLiveData<Int> = MutableLiveData<Int>()


    //성공 = 1, 실패 = 0
    fun signIn(email: String, password : String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                    result->
                if(result.isSuccessful){
                    _signInResponse.value=1
                }else{
                    _signInResponse.value=0
                }
            }
            .addOnFailureListener {
                _signInResponse.value=0

            }
    }
}