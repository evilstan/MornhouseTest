package com.evilstan.mornhousetest.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface NumbersApi  {
    @GET("{number}")
    suspend fun getNumber(@Path("number") number: String): Response<String?>

    @GET("random")
    suspend fun getRandomNumber(): Response<String?>
}