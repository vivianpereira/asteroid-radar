package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidDBItem
import org.json.JSONObject

class AsteroidsRepository(
    private val asteroidApi: AsteroidApi,
    private val dao: AsteroidDao
) {

    suspend fun loadAsteroids(startDate: String, endDate: String) {
        val apiResponse = asteroidApi.service.getAsteroids(startDate, endDate, Constants.API_KEY)
        val json = JSONObject(apiResponse)
        dao.insertAll(*parseAsteroidsJsonResult(json).map {
            AsteroidDBItem(
                id = it.id,
                codename = it.codename,
                closeApproachDate = it.closeApproachDate,
                absoluteMagnitude = it.absoluteMagnitude,
                estimatedDiameter = it.estimatedDiameter,
                relativeVelocity = it.relativeVelocity,
                distanceFromEarth = it.distanceFromEarth,
                isPotentiallyHazardous = it.isPotentiallyHazardous
            )
        }.toTypedArray())
    }

    suspend fun getAsteroids() : List<Asteroid>{
        return dao.getAsteroids().map {
            Asteroid(
                id = it.id,
                codename = it.codename,
                closeApproachDate = it.closeApproachDate,
                absoluteMagnitude = it.absoluteMagnitude,
                estimatedDiameter = it.estimatedDiameter,
                relativeVelocity = it.relativeVelocity,
                distanceFromEarth = it.distanceFromEarth,
                isPotentiallyHazardous = it.isPotentiallyHazardous
            )
        }
    }

    suspend fun getDailyPicture(): PictureOfDay {
        return asteroidApi.service.getDailyPicture(Constants.API_KEY)
    }

}