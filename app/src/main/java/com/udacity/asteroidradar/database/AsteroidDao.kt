package com.udacity.asteroidradar.database

import androidx.room.*

@Dao
interface AsteroidDao {
    @Query("select * from AsteroidDBItem where date(closeApproachDate) >= date(:date) order by closeApproachDate asc")
    fun getAsteroids(date: String): List<AsteroidDBItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroidDBItems: AsteroidDBItem)
}

@Database(entities = [AsteroidDBItem::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}