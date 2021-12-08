package com.udacity.asteroidradar.worker

import android.content.Context
import android.net.wifi.WifiManager
import android.os.BatteryManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository


class AsteroidWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val repository = AsteroidsRepository(
        asteroidApi = AsteroidApi,
        dao = context.getDatabase().asteroidDao
    )

    override suspend fun doWork(): Result {
        return if (isWifiConnect() && isCharging()) {
            try {
                repository.loadAsteroids()
            } catch (ex: Exception) {
                Result.failure()
            }
            Result.success()
        } else {
            Result.success()
        }
    }

    private fun isCharging(): Boolean {
        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as? BatteryManager
        return batteryManager?.isCharging ?: false
    }

    private fun isWifiConnect(): Boolean {
        val wifiMgr =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager
        return if (wifiMgr?.isWifiEnabled == true) { // Wi-Fi adapter is ON
            val wifiInfo = wifiMgr.connectionInfo
            wifiInfo.networkId != -1 // it is always - 1 in emulator, works with real network
        } else {
            false
        }
    }


}