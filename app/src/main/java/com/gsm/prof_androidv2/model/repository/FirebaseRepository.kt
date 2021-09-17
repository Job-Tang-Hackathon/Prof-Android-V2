package com.gsm.prof_androidv2.model.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.gsm.prof_androidv2.model.repository.datasource.FirebaseDataSource
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val dataSource: FirebaseDataSource
) {
    fun getCategoryPost(category: String) = dataSource.getCategoryPost(category)

    fun getMyPost(category: String, uid: String) = dataSource.getMyPost(category, uid)

    fun getSearchedPost(keyword: String) = dataSource.getSearchedPost(keyword)

    fun getSearchMyPost(keyword: String, uid: String) = dataSource.getSearchMyPost(keyword, uid)

    fun photoUpload(status: String, imageUrl: ArrayList<String>) =
        dataSource.photoUpload(status, imageUrl)
}