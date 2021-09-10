package com.gsm.prof_androidv2.model.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.gsm.prof_androidv2.model.repository.datasource.FirebaseDataSource
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val dataSource: FirebaseDataSource
) {
    fun getCategoryPost(category : String) = dataSource.getCategoryPost(category)
}