package com.rodrigoguerrero.wetransfer.repositories

import com.rodrigoguerrero.wetransfer.models.Room
import kotlinx.coroutines.flow.Flow

interface RoomsRepository {

    val rooms : Flow<List<Room>>

    suspend fun bookRoom(room: Room): Flow<RoomsResult>
    suspend fun fetchRooms(): Flow<RoomsResult>
}
