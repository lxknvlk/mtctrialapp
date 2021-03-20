package com.example.mtctrial.ui.main

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIClient {
//    @GET("data/2.5/weather?")
//    fun getCurrentWeatherData(
//        @Query("searchString") searchString: String,
//        @Query("lon") lon: String,
//        @Query("APPID") app_id: String
//    ): Call<WeatherResponse>

    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("APPID") app_id: String
    ): Call<WeatherResponse>

}