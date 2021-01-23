package com.byfrunze.redsoft.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductCacheEntity::class], version = 1, exportSchema = false)
abstract class RedsoftDatabase: RoomDatabase() {
    abstract fun productDao():ProductDao

    companion object{
        val DATABASE_NAME = "redsoft_db"
    }
}