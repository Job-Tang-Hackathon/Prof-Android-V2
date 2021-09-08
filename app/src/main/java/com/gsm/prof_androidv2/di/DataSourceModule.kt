package com.gsm.prof_androidv2.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.gsm.prof_androidv2.model.repository.FirebaseRepository
import com.gsm.prof_androidv2.model.repository.datasource.FirebaseDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Provides
    @Singleton
    fun provideFirebaseDataSource(
        firebaseAuth: FirebaseAuth,
        firebaseRtdb: FirebaseDatabase,
        firebaseStorage: FirebaseStorage
    ): FirebaseDataSource {
        return FirebaseDataSource(firebaseAuth, firebaseRtdb, firebaseStorage)
    }
}