package com.gsm.prof_androidv2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.gsm.prof_androidv2.model.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: FirebaseRepository,
    private val firebaseAuth: FirebaseAuth

    ) : ViewModel() {

    val signUpResponse : LiveData<Int> get() = _signUpResponse
    private val _signUpResponse: MutableLiveData<Int> = MutableLiveData<Int>()


    //0 = 에러, 1 = 성공, 2 = 에러
    fun signUp(email: String, password: String){
         firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    _signUpResponse.value = 1
                } else {
                    _signUpResponse.value = 2
                }
            }
            .addOnFailureListener {
                _signUpResponse.value = 0
            }
    }
}