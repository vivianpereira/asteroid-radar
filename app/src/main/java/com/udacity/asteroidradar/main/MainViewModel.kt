package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid

class MainViewModel : ViewModel() {

    val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids


    fun loadAsteroids() {
        _asteroids.value = listOf(
            Asteroid(
                id = 1,
                codename = "Test Asteroid",
                closeApproachDate = "23/12/2021",
                absoluteMagnitude = 100.0,
                estimatedDiameter = 100.0,
                relativeVelocity = 100.0,
                distanceFromEarth = 100.0,
                isPotentiallyHazardous = true
            ),
            Asteroid(
                id = 2,
                codename = "Test Asteroid 2",
                closeApproachDate = "24/12/2021",
                absoluteMagnitude = 100.0,
                estimatedDiameter = 100.0,
                relativeVelocity = 100.0,
                distanceFromEarth = 100.0,
                isPotentiallyHazardous = true
            )
        )
    }


}