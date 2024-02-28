package com.evilstan.mornhousetest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "numbers")
data class NumberInfo(
    val number: String = "",
    val description: String = "",
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timeStamp: Long = System.currentTimeMillis()
)
