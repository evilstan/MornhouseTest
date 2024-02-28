package com.evilstan.mornhousetest.data.repository

import com.evilstan.mornhousetest.data.database.NumbersDao
import com.evilstan.mornhousetest.data.model.NumberInfo
import com.evilstan.mornhousetest.data.network.NumbersApi
import com.evilstan.mornhousetest.domain.repository.MainRepository

class MainRepositoryImpl(
    private val numbersDao: NumbersDao,
    private val numbersApi: NumbersApi
) : MainRepository {

    override suspend fun getNumber(number: String): NumberInfo? {
        return numbersDao.getNumber(number) ?: getRemoteNumber(number)
    }

    override suspend fun getRandomNumber(): NumberInfo? {
        return getRemoteNumber(null)
    }

    override fun getHistory() = numbersDao.getHistory()

    private suspend fun getRemoteNumber(number: String?): NumberInfo? {
        try {
            val response = if (number == null) numbersApi.getRandomNumber()
            else numbersApi.getNumber(number)

            if (!response.isSuccessful || response.body() == null) return null

            val parts = response.body()!!.split(" ", limit = 2)
            val numberInfo = NumberInfo(parts[0], parts[1])
            numbersDao.insertNumber(numberInfo)

            return numberInfo
        } catch (_: Exception) {
            return null
        }
    }
}