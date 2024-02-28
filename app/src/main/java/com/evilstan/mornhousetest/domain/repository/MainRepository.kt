package com.evilstan.mornhousetest.domain.repository

import com.evilstan.mornhousetest.data.model.NumberInfo
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getNumber(number: String): NumberInfo?
    suspend fun getRandomNumber(): NumberInfo?
    fun getHistory(): Flow<List<NumberInfo>>
}