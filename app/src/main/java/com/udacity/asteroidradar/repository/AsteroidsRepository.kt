package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import org.json.JSONObject

class AsteroidsRepository(
    private val api: AsteroidApi
) {

    suspend fun getAsteroids(startDate: String, endDate: String) : List<Asteroid>{
        val apiResponse = api.service.getAsteroids(startDate, endDate, Constants.API_KEY)
        val json = JSONObject(apiResponse)
        return parseAsteroidsJsonResult(json)
    }
}