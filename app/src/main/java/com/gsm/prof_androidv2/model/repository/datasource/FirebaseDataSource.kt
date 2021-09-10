package com.gsm.prof_androidv2.model.repository.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.gsm.prof_androidv2.model.dto.GetCategoryPostDto
import javax.inject.Inject

class FirebaseDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseRtdb: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage,
    private val firebaseStore : FirebaseFirestore
){
    fun getCategoryPost(category : String) =  firebaseStore.collection(category).get()
}