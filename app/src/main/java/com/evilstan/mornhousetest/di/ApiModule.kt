package com.evilstan.mornhousetest.di

import com.evilstan.mornhousetest.data.network.NumbersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun providesAuthApi(retrofit: Retrofit): NumbersApi = retrofit.create(NumbersApi::class.java)
}