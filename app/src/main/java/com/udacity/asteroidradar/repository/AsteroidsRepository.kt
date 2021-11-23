package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import org.json.JSONObject

class AsteroidsRepository(
    private val asteroidApi: AsteroidApi
) {

    suspend fun getAsteroids(startDate: String, endDate: String) : List<Asteroid>{
        val apiResponse = asteroidApi.service.getAsteroids(startDate, endDate, Constants.API_KEY)
        val json = JSONObject(apiResponse)
        return parseAsteroidsJsonResult(json)
    }

    suspend fun getDailyPicture(): PictureOfDay {
        return asteroidApi.service.getDailyPicture(Constants.API_KEY)
    }

}