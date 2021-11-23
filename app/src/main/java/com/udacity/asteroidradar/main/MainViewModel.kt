package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = AsteroidsRepository(
        asteroidApi = AsteroidApi
    )
    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _urlImage = MutableLiveData<String>()
    val urlImage: LiveData<String>
        get() = _urlImage

    fun loadAsteroids() {
        viewModelScope.launch {
            _asteroids.value = repository.getAsteroids("2021-11-17", "2021-11-23")
            _urlImage.value = repository.getDailyPicture().url
        }
    }

    fun showAsteroidDetails() {
        _asteroids.value = null
    }

}