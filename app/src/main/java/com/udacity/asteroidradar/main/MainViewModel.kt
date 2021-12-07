package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = application.getDatabase()

    private val repository = AsteroidsRepository(
        asteroidApi = AsteroidApi,
        dao = database.asteroidDao
    )
    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _urlImage = MutableLiveData<String>()
    val urlImage: LiveData<String>
        get() = _urlImage

    fun loadAsteroids() {
        viewModelScope.launch(Dispatchers.IO) {
            val today = Date()
            repository.loadAsteroids(today.formatDate(), today.formatDate())
            _asteroids.postValue(repository.getAsteroids())
            _urlImage.postValue(repository.getDailyPicture().url)
        }
    }

    fun showAsteroidDetails() {
        _asteroids.value = null
    }

    private fun Date.formatDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(this)
    }
}