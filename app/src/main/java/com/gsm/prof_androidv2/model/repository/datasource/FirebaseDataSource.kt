package com.gsm.prof_androidv2.model.repository.datasource

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class FirebaseDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseRtdb: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage,
    private val firebaseStore: FirebaseFirestore
) {
    fun getCategoryPost(category: String) = firebaseStore.collection(category).get()

    fun getSearchedPost(keyword : String) = firebaseStore.collection("all").whereEqualTo("title", keyword).get()

    fun getSearchMyPost(keyword : String, uid : String) = firebaseStore.collection("all").whereEqualTo("title", keyword).whereEqualTo("uid", uid).get()

    fun getMyPost(category: String, uid: String): Task<QuerySnapshot> {
        Log.d("로그","선택된 카테고리 : $category")
        return firebaseStore.collection(category).whereEqualTo("uid", uid).get()
    }
}