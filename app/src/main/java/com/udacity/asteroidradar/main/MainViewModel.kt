package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import com.udacity.asteroidradar.worker.AsteroidWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainViewModel(application: Application) : AndroidViewModel(application) {

    init {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueue(
            PeriodicWorkRequest.Builder(
                AsteroidWorker::class.java,
                1,
                TimeUnit.DAYS
            ).build()
        )
//        workManager.enqueue(OneTimeWorkRequestBuilder<AsteroidWorker>().build())
    }

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
            val asteroids = repository.getAsteroids()
            if (asteroids.isEmpty()) {
                repository.loadAsteroids()
                _asteroids.postValue(repository.getAsteroids())
            } else {
                _asteroids.postValue(asteroids)
            }
            _urlImage.postValue(repository.getDailyPicture().url)
        }
    }

    fun showAsteroidDetails() {
        _asteroids.value = null
    }
}