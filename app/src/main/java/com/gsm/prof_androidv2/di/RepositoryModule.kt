package com.gsm.prof_androidv2.di

import com.gsm.prof_androidv2.model.repository.FirebaseRepository
import com.gsm.prof_androidv2.model.repository.datasource.FirebaseDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideFirebaseRepository(
        firebaseDataSource: FirebaseDataSource
    ): FirebaseRepository {
        return FirebaseRepository(firebaseDataSource)
    }
}