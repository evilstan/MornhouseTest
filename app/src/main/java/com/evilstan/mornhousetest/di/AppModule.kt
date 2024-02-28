package com.evilstan.mornhousetest.di

import com.evilstan.mornhousetest.data.database.NumbersDao
import com.evilstan.mornhousetest.data.network.NumbersApi
import com.evilstan.mornhousetest.domain.repository.MainRepository
import com.evilstan.mornhousetest.data.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {
    @Provides
    @ViewModelScoped
    fun provideMainRepository(numbersDao: NumbersDao, numbersApi: NumbersApi): MainRepository =
        MainRepositoryImpl(numbersDao, numbersApi)
}