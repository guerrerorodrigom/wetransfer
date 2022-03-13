package com.rodrigoguerrero.wetransfer.storage

import com.rodrigoguerrero.wetransfer.models.Room
import kotlinx.coroutines.flow.Flow

interface RoomDataSource {

    val rooms: Flow<List<Room>>

    suspend fun addRooms(rooms: List<Room>)

    suspend fun updateRoom(room: Room)
}
