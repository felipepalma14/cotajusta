package com.felipepalma14.cotajusta.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.felipepalma14.cotajusta.data.local.dao.FiiDao
import com.felipepalma14.cotajusta.data.local.entity.FiiEntity

@Database(entities = [FiiEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fiiDao(): FiiDao
}
