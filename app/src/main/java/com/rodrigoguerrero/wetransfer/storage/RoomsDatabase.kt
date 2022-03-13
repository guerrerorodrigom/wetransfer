package com.rodrigoguerrero.wetransfer.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rodrigoguerrero.wetransfer.storage.dao.RoomsDao

@Database(
    entities = [RoomEntity::class],
    version = 1
)
abstract class RoomsDatabase : RoomDatabase() {
    abstract fun roomsDao(): RoomsDao
}
