package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.database.AsteroidDBItem
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AsteroidsRepository(
    private val asteroidApi: AsteroidApi,
    private val dao: AsteroidDao
) {

    suspend fun loadAsteroids() {
        val today = Date()

        val apiResponse = asteroidApi.service.getAsteroids(today.formatDate(), Constants.API_KEY)
        val json = JSONObject(apiResponse)
        val asteroids = parseAsteroidsJsonResult(json)
        dao.insertAll(*asteroids.map {
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

    private fun Date.formatDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(this)
    }

    suspend fun getAsteroids(): List<Asteroid> {
        val today = Date()
        return dao.getAsteroids(today.formatDate()).map {
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