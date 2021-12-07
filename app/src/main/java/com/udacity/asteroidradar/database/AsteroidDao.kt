package com.udacity.asteroidradar.database

import androidx.room.*

@Dao
interface AsteroidDao {
    @Query("select * from AsteroidDBItem")
    fun getAsteroids(): List<AsteroidDBItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroidDBItems: AsteroidDBItem)
}

@Database(entities = [AsteroidDBItem::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}