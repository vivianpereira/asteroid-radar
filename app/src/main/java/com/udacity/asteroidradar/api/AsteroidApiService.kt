package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants
import retrofit2.http.GET
import retrofit2.http.Query


interface AsteroidApiService {

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") key: String = Constants.API_KEY)

    @GET("planetary/apod")
    suspend fun getDailyPicture(
        @Query("api_key") key: String = Constants.API_KEY)
}

