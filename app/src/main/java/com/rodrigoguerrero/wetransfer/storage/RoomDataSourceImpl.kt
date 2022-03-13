package com.rodrigoguerrero.wetransfer.storage

import com.rodrigoguerrero.wetransfer.models.Room
import com.rodrigoguerrero.wetransfer.storage.dao.RoomsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomDataSourceImpl @Inject constructor(
    private val roomsDao: RoomsDao
) : RoomDataSource {

    override val rooms: Flow<List<Room>>
        get() = roomsDao.getRooms().map { list ->
            list.map { entity -> Room(entity.name, entity.spots, entity.thumbnail) }
        }

    override suspend fun addRooms(rooms: List<Room>) {
        val roomsEntities = rooms.map {
            RoomEntity(name = it.name, spots = it.spots, thumbnail = it.thumbnail)
        }
        roomsDao.addRooms(roomsEntities)
    }

    override suspend fun updateRoom(room: Room) {
        roomsDao.updateRoom(RoomEntity(room.name, room.spots, room.thumbnail))
    }
}
