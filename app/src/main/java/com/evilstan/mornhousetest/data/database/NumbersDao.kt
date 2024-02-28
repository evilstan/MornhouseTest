package com.evilstan.mornhousetest.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.evilstan.mornhousetest.data.model.NumberInfo

@Dao
interface NumbersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumber(number: NumberInfo)

    @Query("SELECT * FROM numbers WHERE number == :number")
    suspend fun getNumber(number: String): NumberInfo?

    @Query("SELECT * FROM numbers ORDER BY timeStamp DESC")
    fun getHistory(): Flow<List<NumberInfo>>
}