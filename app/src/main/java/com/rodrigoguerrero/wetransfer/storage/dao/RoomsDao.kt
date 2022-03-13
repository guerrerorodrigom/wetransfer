package com.rodrigoguerrero.wetransfer.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.rodrigoguerrero.wetransfer.models.Room
import com.rodrigoguerrero.wetransfer.storage.RoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomsDao {
    @Query("SELECT * FROM rooms")
    fun getRooms(): Flow<List<RoomEntity>>

    @Insert(onConflict = REPLACE)
    fun addRooms(rooms: List<RoomEntity>)

    @Update
    fun updateRoom(room: RoomEntity)
}
